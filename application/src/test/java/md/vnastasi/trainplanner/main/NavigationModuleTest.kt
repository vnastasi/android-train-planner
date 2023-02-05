package md.vnastasi.trainplanner.main

import android.app.Application
import com.nhaarman.mockitokotlin2.mock
import md.vnastasi.trainplanner.login.LoginModuleDefinition
import md.vnastasi.trainplanner.splash.SplashModuleDefinition
import org.junit.jupiter.api.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.logger.PrintLogger
import org.koin.test.check.checkModules
import org.mockito.Mockito

internal class NavigationModuleTest {

    @Test
    internal fun testModule() {
        val mockApplication = mock<Application>(defaultAnswer = Mockito.RETURNS_DEEP_STUBS)

        val koinApplication = startKoin {
            logger(PrintLogger())
            koin.loadModules(
                listOf(
                    SplashModuleDefinition.module,
                    LoginModuleDefinition(mockApplication).module
                )
            )
        }

        koinApplication.checkModules()

        stopKoin()
    }
}
