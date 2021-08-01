package md.vnastasi.trainplanner.login

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import md.vnastasi.trainplaner.di.ModuleDefinition
import md.vnastasi.trainplanner.async.DispatcherRegistry
import md.vnastasi.trainplanner.login.repository.AuthenticationRepository
import md.vnastasi.trainplanner.login.repository.CredentialsStorageRepository
import md.vnastasi.trainplanner.login.repository.CredentialsStorageRepository.Companion.DATA_STORE_NAME
import md.vnastasi.trainplanner.login.repository.CredentialsStorageRepositoryImpl
import md.vnastasi.trainplanner.login.repository.LocalAuthenticationRepositoryImpl
import md.vnastasi.trainplanner.login.ui.LoginViewModel
import md.vnastasi.trainplanner.login.usecase.CheckCredentialsUseCase
import md.vnastasi.trainplanner.login.usecase.EncodeCredentialsUseCase
import md.vnastasi.trainplanner.login.usecase.PerformAuthenticationUseCase
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.dsl.module

class LoginModule(
    private val application: Application
) : ModuleDefinition {

    override val module: Module = module {

        factory<AuthenticationRepository> {
            LocalAuthenticationRepositoryImpl()
        }

        factory<CredentialsStorageRepository> {
            CredentialsStorageRepositoryImpl(application.dataStore)
        }

        factory {
            EncodeCredentialsUseCase()
        }

        factory {
            CheckCredentialsUseCase(credentialsStorageRepository = get())
        }

        factory {
            PerformAuthenticationUseCase(
                authenticationRepository = get(),
                credentialsStorageRepository = get(),
                encodeCredentialsUseCase = get()
            )
        }

        factory {
            LoginViewModel.Provider(
                performAuthenticationUseCase = get(),
                checkCredentialsUseCase = get()
            )
        }
    }

    private val Context.dataStore by preferencesDataStore(name = DATA_STORE_NAME)
}
