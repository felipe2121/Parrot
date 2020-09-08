package br.felipe.parrot.domain._config.repository

import br.felipe.parrot.core.util.ParrotResult
import br.felipe.parrot.domain.BuildConfig
import br.felipe.parrot.domain._config.exepetion.toNetworkException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

abstract class ParrotRepository {

    // network data
    abstract class Remote {

        fun getHttpCaller(): Retrofit {

            val clientBuilder = OkHttpClient.Builder().apply {
                if (BuildConfig.DEBUG) {
                    addInterceptor(HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    })
                }
            }

            return Retrofit.Builder()
                .baseUrl("http://contatosapi.herokuapp.com/api/")
                .addConverterFactory(GsonConverterFactory.create()) // convert JSON in kotlin class
                .build()
        }

        // lazy initialisation
        inline fun<reified T> retrofit() = lazy {
            getHttpCaller().create(T::class.java)
        }

        suspend fun <API, T> executeRequest(api: API, request: suspend API.() -> T): ParrotResult<T> {

            var repositoryResult: ParrotResult<T>? = null

            CoroutineScope(Dispatchers.IO).launch {
                repositoryResult = try {
                    ParrotResult.success(api.request())
                } catch (error: Exception) {
                    ParrotResult.failure(error.toNetworkException())
                }
            }.join()

            return repositoryResult!!
        }
    }

    // room data
    abstract class Local {

    }

}