package md.vnastasi.trainplanner.async

import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import org.junit.jupiter.api.extension.*

class TestCoroutineScopeExtension : BeforeEachCallback, AfterEachCallback, ParameterResolver {

    private val dispatcher = TestCoroutineDispatcher()
    private val coroutineScope = TestCoroutineScope(dispatcher + SupervisorJob())

    override fun beforeEach(context: ExtensionContext?) {
        DispatcherRegistry.overrideMain(dispatcher)
        DispatcherRegistry.overrideDefault(dispatcher)
        DispatcherRegistry.overrideIO(dispatcher)
        Thread.setDefaultUncaughtExceptionHandler { _, exception -> throw exception }
    }

    override fun afterEach(context: ExtensionContext?) {
        dispatcher.cleanupTestCoroutines()
        DispatcherRegistry.reset()
        Thread.setDefaultUncaughtExceptionHandler(null)
    }

    @Suppress("NewApi")
    override fun supportsParameter(parameterContext: ParameterContext?, extensionContext: ExtensionContext?): Boolean =
        parameterContext?.parameter?.type == TestCoroutineScope::class.java

    override fun resolveParameter(parameterContext: ParameterContext?, extensionContext: ExtensionContext?): Any = coroutineScope
}
