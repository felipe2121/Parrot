package br.felipe.parrot.domain._config.repository

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class Repository {

    // network data
    abstract class Remote {

        fun getHttpCaller(): Retrofit {
            return Retrofit.Builder()
                .baseUrl("http://contatosapi.herokuapp.com/api/")
                .addConverterFactory(GsonConverterFactory.create()) // convert JSON in kotlin class
                .build()
        }

        // lazy initialisation
        inline fun<reified T> retrofit() = lazy {
            getHttpCaller().create(T::class.java)
        }
    }

    // room data
    abstract class Local {

    }

}