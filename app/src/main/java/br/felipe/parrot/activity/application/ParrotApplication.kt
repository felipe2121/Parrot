package br.felipe.parrot.activity.application

import android.app.Application
import android.util.Log
import br.felipe.parrot.activity.viewmodel.CreateContactViewModel
import br.felipe.parrot.activity.viewmodel.LoginViewModel
import br.felipe.parrot.activity.viewmodel.MainViewModel
import br.felipe.parrot.activity.viewmodel.SingInViewModel
import br.felipe.parrot.data._config.ParrotDatabase
import br.felipe.parrot.data._config.ParrotDatabase.Companion.dropDatabase
import br.felipe.parrot.domain.repository.ParrotRemoteRepository
import br.felipe.parrot.domain.repository.UserRepository
import br.felipe.parrot.domain.repository.ParrotLocalRepository
import br.felipe.parrot.domain.usecase.CreateContactUseCase
import br.felipe.parrot.domain.usecase.LoginUseCase
import br.felipe.parrot.domain.usecase.LogoutUseCase
import br.felipe.parrot.domain.usecase.SignInUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

class ParrotApplication:Application(), DeleteDataBase {

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
                factory { UserRepository(parrotLocalRepository = get(), parrotRemoteRepository = get()) }
            }

            val useCaseModule = module {
                factory { LoginUseCase(userRepository = get()) }
                factory { SignInUseCase(userRepository = get()) }
                factory { LogoutUseCase(userRepository = get()) }
                factory { CreateContactUseCase(userRepository = get()) }
            }

            val viewModelModule = module {
                factory { LoginViewModel(loginUseCase = get()) }
                factory { SingInViewModel(singIn = get()) }
                factory { MainViewModel(logoutUseCase = get()) }
                factory { CreateContactViewModel(createContactUseCase = get()) }
            }

            val inValidateModule = module {
                single<DeleteDataBase> { this@ParrotApplication }
            }

            modules(listOf(daoModule, repositoryModule, useCaseModule, viewModelModule, inValidateModule))
        }
    }

    override suspend fun inValidate() {
        dropDatabase()
    }
}