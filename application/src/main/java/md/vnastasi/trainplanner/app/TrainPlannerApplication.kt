package md.vnastasi.trainplanner.app

import android.app.Application
import android.net.ConnectivityManager
import md.vnastasi.trainplaner.di.initModules
import md.vnastasi.trainplanner.BuildConfig
import md.vnastasi.trainplanner.api.ApiModuleDefinition
import md.vnastasi.trainplanner.api.auth.Authorization
import md.vnastasi.trainplanner.dashboard.disruption.DisruptionModuleDefinition
import md.vnastasi.trainplanner.login.LoginModuleDefinition
import md.vnastasi.trainplanner.splash.SplashModuleDefinition

class TrainPlannerApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initModules(
            ApiModuleDefinition(ApiModuleDefinition.Configuration(
                baseUrl = BuildConfig.API_BASE_URL,
                authorizationProvider = { Authorization(username = BuildConfig.APP_USER, password = BuildConfig.APP_PASSWORD) },
                connectivityChecker = { getSystemService(ConnectivityManager::class.java).activeNetwork != null },
                cacheDir = cacheDir,
                isLoggingAllowed = BuildConfig.DEBUG
            )),
            SplashModuleDefinition,
            LoginModuleDefinition(this),
            DisruptionModuleDefinition
        )
    }
}
