package md.vnastasi.trainplanner.api.util

import md.vnastasi.trainplanner.api.ApiModuleDefinition
import md.vnastasi.trainplanner.api.auth.Authorization
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.extension.*
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.logger.PrintLogger
import java.io.File

class WebServerExtension(
        private val connectivityChecker: () -> Boolean = { true },
        private val authorizationProvider: () -> Authorization = { Authorization("", "") }
) : BeforeAllCallback, AfterAllCallback, ParameterResolver {

    private val webServer = MockWebServer()

    override fun beforeAll(context: ExtensionContext?) {
        webServer.start()

        val configuration = ApiModuleDefinition.Configuration(
                baseUrl = webServer.url("/").toUrl().toString(),
                isLoggingAllowed = true,
                connectivityChecker = connectivityChecker,
                authorizationProvider = authorizationProvider,
                cacheDir = File(requireNotNull(System.getProperty("java.io.tmpdir")))
        )

        startKoin {
            logger(PrintLogger())
            koin.loadModules(listOf(ApiModuleDefinition(configuration).module))
        }
    }

    override fun afterAll(context: ExtensionContext?) {
        webServer.shutdown()
        stopKoin()
    }

    override fun supportsParameter(parameterContext: ParameterContext?, extensionContext: ExtensionContext?): Boolean =
            parameterContext?.parameter?.type == MockWebServer::class.java

    override fun resolveParameter(parameterContext: ParameterContext?, extensionContext: ExtensionContext?): Any =
            webServer
}
