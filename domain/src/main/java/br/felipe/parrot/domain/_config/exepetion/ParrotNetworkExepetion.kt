package br.felipe.parrot.domain._config.exepetion

import br.felipe.parrot.core.exception.ParrotException
import br.felipe.parrot.core.uil.StringWrapper
import br.felipe.parrot.domain.R
import retrofit2.HttpException
import java.net.*

fun Throwable.toNetworkException() = ParrotNetworkException(message, this)

fun Throwable.toNetworkExceptionCode(): ParrotNetworkException.NetworkExceptionCode {

    return when (this) {

        is HttpException -> {
            when (code()) {
                HttpURLConnection.HTTP_BAD_REQUEST -> ParrotNetworkException.NetworkExceptionCode.BAD_REQUEST
                HttpURLConnection.HTTP_UNAUTHORIZED -> ParrotNetworkException.NetworkExceptionCode.UNAUTHORIZED
                HttpURLConnection.HTTP_FORBIDDEN -> ParrotNetworkException.NetworkExceptionCode.FORBIDDEN
                HttpURLConnection.HTTP_NOT_FOUND -> ParrotNetworkException.NetworkExceptionCode.NOT_FOUND
                in 400..499 -> ParrotNetworkException.NetworkExceptionCode.CLIENT
                in 500..599 -> ParrotNetworkException.NetworkExceptionCode.SERVER
                else -> ParrotNetworkException.NetworkExceptionCode.UNEXPECTED
            }
        }
        is SocketTimeoutException -> ParrotNetworkException.NetworkExceptionCode.TIMEOUT
        is UnknownHostException -> ParrotNetworkException.NetworkExceptionCode.UNKNOWN_HOST
        is ConnectException -> ParrotNetworkException.NetworkExceptionCode.CONNECTION
        is SocketException -> ParrotNetworkException.NetworkExceptionCode.CANCELED
        else -> ParrotNetworkException.NetworkExceptionCode.UNEXPECTED
    }
}

fun createNetworkException(errorCode: ParrotNetworkException.NetworkExceptionCode): ParrotNetworkException {
    return ParrotNetworkException().apply { this.errorCode = errorCode }
}

class ParrotNetworkException : ParrotException {

    /**
     *  Códigos de erro:
     *
     *  #CLIENT - Um erro na faixa de 400 .. 499
     *  #SERVER - Um erro na faixa de 500 .. 599
     *
     *  #400/BAD_REQUEST - O request é invalido ou não pode ser servido. Geralmente o JSON pode não ser
     *  válido.
     *  #401/UNAUTHORIZED - A requisição requer autenticação do usuário.
     *  #403/FORBIDDEN - O servidor entende a requisição mas o acesso não está liberado.
     *  #404/NOT_FOUND - Não foi encontrado o que se procura.
     *
     *  #TIMEOUT - Esgotou o tempo limite da requisição.
     *  #UNKNOWN_HOST - O endereço IP do host não foi encontrado, acontece também quando não está
     *  conectado a internet.
     *  #CONNECTION - Sem conexão com a internet.
     *  #CANCELED - A requisição foi cancelada, normalmente o usuário recebeu UNAUTHORIZED em outra
     *  requisição paralela a essa.
     *
     *  #UNEXPECTED - Um erro inesperado.
     *
     * */

    enum class NetworkExceptionCode(val message: StringWrapper) {
        CLIENT(StringWrapper(R.string.network_error_try_again)),
        SERVER(StringWrapper(R.string.network_error_server)),
        BAD_REQUEST(StringWrapper(R.string.network_error_bad_request)),
        UNAUTHORIZED(StringWrapper(R.string.network_error_unauthorized)),
        FORBIDDEN(StringWrapper(R.string.network_error_try_again)),
        NOT_FOUND(StringWrapper(R.string.network_error_not_found)),
        TIMEOUT(StringWrapper(R.string.network_error_try_again)),
        UNKNOWN_HOST(StringWrapper(R.string.network_error_connection)),
        CONNECTION(StringWrapper(R.string.network_error_connection)),
        CANCELED(StringWrapper(R.string.network_error_canceled)),
        UNEXPECTED(StringWrapper(R.string.network_error_try_again))
    }

    override var errorMessage: StringWrapper

    var errorCode: NetworkExceptionCode

    constructor() : super()
    constructor(message: String?, cause: Throwable?) : super(message, cause)

    init {
        errorCode = cause?.toNetworkExceptionCode() ?: NetworkExceptionCode.UNEXPECTED
        errorMessage = errorCode.message
    }
}