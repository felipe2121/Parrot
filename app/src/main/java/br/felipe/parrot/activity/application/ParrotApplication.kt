package br.felipe.parrot.activity.application

import android.app.Application
import br.felipe.parrot.data._config.ParrotDatabase
import br.felipe.parrot.domain.repository.ParrotRemoteRepository
import br.felipe.parrot.domain.repository.UserRepository
import br.felipe.parrot.domain.repository.ParrotLocalRepository
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

class ParrotApplication:Application() {

    override fun onCreate() {
        super.onCreate()
        setUpKoin()
    }

    private fun setUpKoin () {

        startKoin {

            androidContext(this@ParrotApplication)

            val daoModule = module {
                single { ParrotDatabase.getInstance(get()) }
                single { get<ParrotDatabase>().userDAO }
            }

            val repositoryModule = module {
                factory { ParrotRemoteRepository() }
                factory { ParrotLocalRepository(userDAO = get()) }
                factory { UserRepository(parrotLocalRepository = get(), loginRemoteRepository = get()) }
            }


            modules(listOf(daoModule, repositoryModule))
        }
    }
}