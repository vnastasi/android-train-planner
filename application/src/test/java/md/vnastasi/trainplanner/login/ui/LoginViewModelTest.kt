package md.vnastasi.trainplanner.login.ui

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import md.vnastasi.trainplanner.async.AsyncResult
import md.vnastasi.trainplanner.async.TestCoroutineScopeExtension
import md.vnastasi.trainplanner.exception.ApplicationException
import md.vnastasi.trainplanner.login.repository.AuthenticationFailureReason
import md.vnastasi.trainplanner.login.usecase.PerformAuthenticationUseCase
import md.vnastasi.trainplanner.test.core.doReturnFlowOf
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

private const val USERNAME = "user"
private const val PASSWORD = "password"

@ExtendWith(TestCoroutineScopeExtension::class)
internal class LoginViewModelTest {

    private val mockPerformAuthenticationUseCase = mock<PerformAuthenticationUseCase>()

    private val viewModel = LoginViewModel(mockPerformAuthenticationUseCase)

    @Test
    internal fun testAuthenticationFailure(scope: TestCoroutineScope) = scope.runBlockingTest {
        whenever(mockPerformAuthenticationUseCase.execute(USERNAME, PASSWORD)).doReturnFlowOf(AsyncResult.Failure(ApplicationException(AuthenticationFailureReason.INVALID_CREDENTIALS)))

        viewModel.viewState.test {
            viewModel.onLogin(USERNAME, PASSWORD)

            assertThat(awaitItem()).isEqualTo(LoginUiStateModel.Pending)
            assertThat(awaitItem()).isEqualTo(LoginUiStateModel.AuthenticationFailed(AuthenticationFailureReason.INVALID_CREDENTIALS))

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    internal fun testAuthenticationSuccess(scope: TestCoroutineScope) = scope.runBlockingTest {
        whenever(mockPerformAuthenticationUseCase.execute(USERNAME, PASSWORD)).doReturnFlowOf(AsyncResult.Success(Unit))

        viewModel.viewState.test {
            viewModel.onLogin(USERNAME, PASSWORD)

            assertThat(awaitItem()).isEqualTo(LoginUiStateModel.Pending)
            assertThat(awaitItem()).isEqualTo(LoginUiStateModel.Authenticated)

            cancelAndConsumeRemainingEvents()
        }
    }
}
