package md.vnastasi.trainplanner.splash

import md.vnastasi.trainplaner.di.ModuleDefinition
import md.vnastasi.trainplanner.splash.usecase.CheckCredentialsUseCase
import md.vnastasi.trainplanner.splash.ui.SplashViewModel
import org.koin.core.module.Module
import org.koin.dsl.module

object SplashModule : ModuleDefinition {

    override val module: Module = module {

        factory {
            CheckCredentialsUseCase(credentialsStorageRepository = get())
        }

        factory {
            SplashViewModel.Provider(checkCredentialsUseCase = get())
        }
    }
}
