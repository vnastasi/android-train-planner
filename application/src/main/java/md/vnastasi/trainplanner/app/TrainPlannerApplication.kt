package md.vnastasi.trainplanner.app

import android.app.Application
import md.vnastasi.trainplaner.di.initModules
import md.vnastasi.trainplanner.login.LoginModule
import md.vnastasi.trainplanner.main.NavigationModule

class TrainPlannerApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initModules(
            NavigationModule,
            LoginModule
        )
    }
}
