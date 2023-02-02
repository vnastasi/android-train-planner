package md.vnastasi.trainplanner.login.ui

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import md.vnastasi.trainplanner.async.AsyncResult
import md.vnastasi.trainplanner.async.Event
import md.vnastasi.trainplanner.async.TestCoroutineScopeExtension
import md.vnastasi.trainplanner.exception.ApplicationException
import md.vnastasi.trainplanner.login.nav.LoginNavigationRoute
import md.vnastasi.trainplanner.login.repository.AuthenticationFailureReason
import md.vnastasi.trainplanner.login.usecase.PerformAuthenticationUseCase
import md.vnastasi.trainplanner.test.core.doReturnFlowOf
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

private const val USERNAME = "user"
private const val PASSWORD = "password"

@ExtendWith(TestCoroutineScopeExtension::class)
internal class LoginViewModelTest {

    private val mockPerformAuthenticationUseCase = mock<PerformAuthenticationUseCase>()

    private val viewModel = LoginViewModel(mockPerformAuthenticationUseCase)

    @Test
    @DisplayName("Given authentication fails Then expect 'AuthenticationFailed' state")
    internal fun testAuthenticationFailure() = runTest {
        whenever(mockPerformAuthenticationUseCase.execute(USERNAME, PASSWORD)).doReturnFlowOf(AsyncResult.Failure(ApplicationException(AuthenticationFailureReason.INVALID_CREDENTIALS)))

            viewModel.viewState.test {
                viewModel.onLogin(USERNAME, PASSWORD)

                assertThat(awaitItem()).isEqualTo(LoginViewState.Init)
                assertThat(awaitItem()).isEqualTo(LoginViewState.AuthenticationFailed(AuthenticationFailureReason.INVALID_CREDENTIALS))

                cancelAndConsumeRemainingEvents()
            }

    }

    @Test
    @DisplayName("Given authentication fails Then expect 'Authenticated' state")
    internal fun testAuthenticationSuccess() = runTest {
        whenever(mockPerformAuthenticationUseCase.execute(USERNAME, PASSWORD)).doReturnFlowOf(AsyncResult.Success(Unit))

        viewModel.viewState.test {
            viewModel.onLogin(USERNAME, PASSWORD)

            assertThat(awaitItem()).isEqualTo(LoginViewState.Init)
            assertThat(awaitItem()).isEqualTo(LoginViewState.Authenticated)

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    @DisplayName("When navigating to dashboard Then expect 'Dashboard' event")
    internal fun testNavigation() = runTest {
        viewModel.navigationRoute.test {
            viewModel.navigateToDashboard()

            assertThat(awaitItem()).isEqualTo(Event(LoginNavigationRoute.Dashboard))

            cancelAndConsumeRemainingEvents()
        }
    }
}
