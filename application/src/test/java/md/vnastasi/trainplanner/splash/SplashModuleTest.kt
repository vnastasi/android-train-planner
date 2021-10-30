package md.vnastasi.trainplanner.splash

import android.app.Application
import com.nhaarman.mockitokotlin2.mock
import md.vnastasi.trainplanner.login.LoginModule
import org.junit.jupiter.api.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.logger.PrintLogger
import org.koin.test.check.checkModules
import org.mockito.Mockito

internal class SplashModuleTest {

    @Test
    internal fun testModule() {
        val mockApplication = mock<Application>(defaultAnswer = Mockito.RETURNS_DEEP_STUBS)

        val koinApplication = startKoin {
            logger(PrintLogger())
            koin.loadModules(
                listOf(
                    LoginModule(mockApplication).module,
                    SplashModule.module
                )
            )
        }

        koinApplication.checkModules()

        stopKoin()
    }
}
