package md.vnastasi.trainplanner.main

import md.vnastasi.trainplaner.di.ModuleDefinition
import org.koin.core.module.Module
import org.koin.dsl.module

object NavigationModule : ModuleDefinition {

    override val module: Module = module {

        factory {
            MainNavHostFragmentFactory(
                loginViewModelProvider = get()
            )
        }
    }
}
