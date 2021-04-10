package md.vnastasi.trainplanner.persistence

import android.app.Application
import androidx.room.Room
import md.vnastasi.trainplaner.di.ModuleDefinition
import md.vnastasi.trainplanner.persistence.client.StationDbClient
import md.vnastasi.trainplanner.persistence.client.impl.StationDbClientImpl
import org.koin.core.module.Module
import org.koin.dsl.module

class PersistenceModuleDefinition(
        private val application: Application
) : ModuleDefinition{

    override val module: Module = module {

        single {
            Room.databaseBuilder(application, ApplicationDatabase::class.java, "train_planner_schema").build()
        }

        single {
            get<ApplicationDatabase>().stationDao
        }

        factory<StationDbClient> {
            StationDbClientImpl(get())
        }
    }
}
