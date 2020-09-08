package br.felipe.parrot.core.exception

import br.felipe.parrot.core.util.StringWrapper

open class ParrotException: Exception {

    constructor(message: String?, cause: Throwable?) : super(message, cause)
    constructor(message: String?) : super(message)
    constructor(cause: Throwable?) : super(cause)
    constructor() : super()
    constructor(
        message: String?,
        cause: Throwable?,
        enableSuppression: Boolean,
        writableStackTrace: Boolean
    ) : super(message, cause, enableSuppression, writableStackTrace)

    open var errorMessage: StringWrapper = StringWrapper("Ocorreu um erro inesperado")
}