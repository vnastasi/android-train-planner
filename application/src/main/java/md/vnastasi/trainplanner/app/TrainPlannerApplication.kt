package md.vnastasi.trainplanner.app

import android.app.Application
import md.vnastasi.trainplaner.di.initModules
import md.vnastasi.trainplanner.login.LoginModule
import md.vnastasi.trainplanner.main.NavigationModule
import md.vnastasi.trainplanner.splash.SplashModule

class TrainPlannerApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initModules(
            NavigationModule,
            SplashModule,
            LoginModule(this)
        )
    }
}
