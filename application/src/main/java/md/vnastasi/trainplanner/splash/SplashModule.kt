package md.vnastasi.trainplanner.splash

import md.vnastasi.trainplaner.di.ModuleDefinition
import md.vnastasi.trainplanner.splash.usecase.impl.CheckCredentialsUseCaseImpl
import md.vnastasi.trainplanner.splash.ui.SplashViewModel
import md.vnastasi.trainplanner.splash.usecase.CheckCredentialsUseCase
import org.koin.core.module.Module
import org.koin.dsl.module

object SplashModule : ModuleDefinition {

    override val module: Module = module {

        factory<CheckCredentialsUseCase> {
            CheckCredentialsUseCaseImpl(credentialsStorageRepository = get())
        }

        factory {
            SplashViewModel.Provider(checkCredentialsUseCase = get())
        }
    }
}
