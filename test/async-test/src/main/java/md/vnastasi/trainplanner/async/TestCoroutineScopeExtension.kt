package md.vnastasi.trainplanner.async

import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext

class TestCoroutineScopeExtension : BeforeEachCallback, AfterEachCallback {

    private val dispatcher = UnconfinedTestDispatcher()

    override fun beforeEach(context: ExtensionContext?) {
        DispatcherRegistry.overrideMain(dispatcher)
        DispatcherRegistry.overrideDefault(dispatcher)
        DispatcherRegistry.overrideIO(dispatcher)
        Thread.setDefaultUncaughtExceptionHandler { _, exception -> throw exception }
    }

    override fun afterEach(context: ExtensionContext?) {
        DispatcherRegistry.reset()
        Thread.setDefaultUncaughtExceptionHandler(null)
    }
}
