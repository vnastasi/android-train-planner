package md.vnastasi.trainplanner.splash.ui

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import md.vnastasi.trainplanner.async.TestCoroutineScopeExtension
import md.vnastasi.trainplanner.splash.usecase.CheckCredentialsUseCase
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(TestCoroutineScopeExtension::class)
internal class SplashViewModelTest {

    private val mockCheckCredentialsUseCase = mock<CheckCredentialsUseCase>()

    private val viewModel by lazy { SplashViewModel(mockCheckCredentialsUseCase) }

    @Test
    internal fun testAuthenticated(scope: TestCoroutineScope) = scope.runBlockingTest {
        whenever(mockCheckCredentialsUseCase.execute()).doReturn(true)

        pauseDispatcher()
        viewModel.authenticationState.test {
            resumeDispatcher()

            assertThat(awaitItem()).isEqualTo(AuthenticationState.Pending)
            assertThat(awaitItem()).isEqualTo(AuthenticationState.Authenticated)

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    internal fun testAnonymous(scope: TestCoroutineScope) = scope.runBlockingTest {
        whenever(mockCheckCredentialsUseCase.execute()).doReturn(false)

        pauseDispatcher()
        viewModel.authenticationState.test {
            resumeDispatcher()

            assertThat(awaitItem()).isEqualTo(AuthenticationState.Pending)
            assertThat(awaitItem()).isEqualTo(AuthenticationState.Anonymous)

            cancelAndConsumeRemainingEvents()
        }
    }
}
