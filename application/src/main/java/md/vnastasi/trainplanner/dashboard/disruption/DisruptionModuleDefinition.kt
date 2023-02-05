package md.vnastasi.trainplanner.dashboard.disruption

import md.vnastasi.trainplaner.di.ModuleDefinition
import md.vnastasi.trainplanner.dashboard.disruption.repository.DisruptionsRepository
import md.vnastasi.trainplanner.dashboard.disruption.repository.impl.DisruptionsRepositoryImpl
import md.vnastasi.trainplanner.dashboard.disruption.ui.DisruptionListFragment
import md.vnastasi.trainplanner.dashboard.disruption.ui.DisruptionListViewModel
import md.vnastasi.trainplanner.dashboard.disruption.usecase.CreateDisruptionListItemsUseCase
import md.vnastasi.trainplanner.dashboard.disruption.usecase.impl.CreateDisruptionListItemsUseCaseImpl
import org.koin.androidx.fragment.dsl.fragment
import org.koin.core.module.Module
import org.koin.dsl.module

object DisruptionModuleDefinition : ModuleDefinition {

    override val module: Module = module {

        factory<DisruptionsRepository> {
            DisruptionsRepositoryImpl(get())
        }

        factory<CreateDisruptionListItemsUseCase> {
            CreateDisruptionListItemsUseCaseImpl(get())
        }

        factory {
            DisruptionListViewModel.Provider(get())
        }

        fragment {
            DisruptionListFragment(get())
        }
    }
}
