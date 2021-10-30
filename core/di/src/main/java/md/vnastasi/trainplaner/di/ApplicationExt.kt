package md.vnastasi.trainplaner.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

fun Application.initModules(vararg moduleDefinitions: ModuleDefinition) {
    startKoin {
        androidContext(this@initModules)
        androidLogger(Level.ERROR)
        modules(moduleDefinitions.map { it.module })
    }
}
