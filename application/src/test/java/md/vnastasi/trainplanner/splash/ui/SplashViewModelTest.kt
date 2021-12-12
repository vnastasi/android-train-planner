package md.vnastasi.trainplanner.splash.ui

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import md.vnastasi.trainplanner.async.Event
import md.vnastasi.trainplanner.async.TestCoroutineScopeExtension
import md.vnastasi.trainplanner.splash.nav.SplashNavigationRoute
import md.vnastasi.trainplanner.splash.usecase.impl.CheckCredentialsUseCaseImpl
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(TestCoroutineScopeExtension::class)
internal class SplashViewModelTest {

    private val mockCheckCredentialsUseCase = mock<CheckCredentialsUseCaseImpl>()

    private val viewModel by lazy(LazyThreadSafetyMode.NONE) { SplashViewModel(mockCheckCredentialsUseCase) }

    @Test
    @DisplayName("Given authenticated Then expect route 'Dashboard'")
    internal fun testAuthenticated(scope: TestCoroutineScope) = scope.runBlockingTest {
        whenever(mockCheckCredentialsUseCase.execute()).doReturn(true)

        pauseDispatcher()
        viewModel.navigationRoute.test {
            resumeDispatcher()

            assertThat(awaitItem()).isEqualTo(Event(SplashNavigationRoute.Dashboard))

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    @DisplayName("Given not authenticated Then expect route 'Login'")
    internal fun testAnonymous(scope: TestCoroutineScope) = scope.runBlockingTest {
        whenever(mockCheckCredentialsUseCase.execute()).doReturn(false)

        pauseDispatcher()
        viewModel.navigationRoute.test {
            resumeDispatcher()

            assertThat(awaitItem()).isEqualTo(Event(SplashNavigationRoute.Login))

            cancelAndConsumeRemainingEvents()
        }
    }
}
