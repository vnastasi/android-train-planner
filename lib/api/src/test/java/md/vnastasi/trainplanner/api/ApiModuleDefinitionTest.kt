package md.vnastasi.trainplanner.api

import md.vnastasi.trainplanner.api.auth.Authorization
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.logger.PrintLogger
import org.koin.test.check.checkModules
import java.io.File

internal class ApiModuleDefinitionTest {

    @Test
    @DisplayName("""
        Given a configuration for API module
        When Koin app is initialized
        Then expect dependency graph to be satisfied
    """)
    internal fun testModule() {
        val config = ApiModuleDefinition.Configuration(
                baseUrl = "https://www.google.com",
                authorizationProvider = { Authorization("user", "password") },
                connectivityChecker = { true },
                isLoggingAllowed = true,
                cacheDir = File(requireNotNull(System.getProperty("java.io.tmpdir")))
        )

        val application = startKoin {
            logger(PrintLogger())
            koin.loadModules(listOf(ApiModuleDefinition(config).module))
        }

        application.checkModules()

        stopKoin()
    }
}
