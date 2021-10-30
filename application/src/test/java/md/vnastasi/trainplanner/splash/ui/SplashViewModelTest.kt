package md.vnastasi.trainplanner.splash.ui

import assertk.all
import assertk.assertions.hasSize
import assertk.assertions.isEqualTo
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import md.vnastasi.trainplanner.async.TestCoroutineScopeExtension
import md.vnastasi.trainplanner.splash.usecase.CheckCredentialsUseCase
import md.vnastasi.trainplanner.test.core.consumingFow
import md.vnastasi.trainplanner.test.core.hasData
import md.vnastasi.trainplanner.test.core.hasItemAtPosition
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(TestCoroutineScopeExtension::class)
internal class SplashViewModelTest {

    private val mockCheckCredentialsUseCase = mock<CheckCredentialsUseCase>()

    private val viewModel by lazy { SplashViewModel(mockCheckCredentialsUseCase) }

    @Test
    internal fun testAuthenticated(scope: TestCoroutineScope) = scope.runBlockingTest {
        whenever(mockCheckCredentialsUseCase.execute()).doReturn(true)

        consumingFow(limit = 2) { viewModel.authenticationState }
            .hasData()
            .all {
                hasSize(2)
                hasItemAtPosition(0).isEqualTo(AuthenticationState.Pending)
                hasItemAtPosition(1).isEqualTo(AuthenticationState.Authenticated)
            }
    }

    @Test
    internal fun testAnonymous(scope: TestCoroutineScope) = scope.runBlockingTest {
        whenever(mockCheckCredentialsUseCase.execute()).doReturn(false)

        consumingFow(limit = 2) { viewModel.authenticationState }
            .hasData()
            .all {
                hasSize(2)
                hasItemAtPosition(0).isEqualTo(AuthenticationState.Pending)
                hasItemAtPosition(1).isEqualTo(AuthenticationState.Anonymous)
            }
    }
}