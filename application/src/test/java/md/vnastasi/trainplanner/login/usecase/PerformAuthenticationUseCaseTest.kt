package md.vnastasi.trainplanner.login.usecase

import assertk.all
import assertk.assertions.hasSize
import assertk.assertions.isEqualTo
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import md.vnastasi.trainplanner.async.AsyncResult
import md.vnastasi.trainplanner.async.TestCoroutineScopeExtension
import md.vnastasi.trainplanner.exception.ApplicationException
import md.vnastasi.trainplanner.login.repository.AuthenticationFailureReason
import md.vnastasi.trainplanner.login.repository.AuthenticationRepository
import md.vnastasi.trainplanner.login.repository.CredentialsStorageRepository
import md.vnastasi.trainplanner.test.core.consumingFow
import md.vnastasi.trainplanner.test.core.hasData
import md.vnastasi.trainplanner.test.core.hasItemAtPosition
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

private const val STUB_USER_NAME = "qwerty"
private const val STUB_PASSWORD = "1234567890"
private const val STUB_CREDENTIALS = "ERESTDTFYHM"

@ExtendWith(TestCoroutineScopeExtension::class)
internal class PerformAuthenticationUseCaseTest {

    private val mockAuthenticationRepository = mock<AuthenticationRepository>()
    private val mockCredentialsStorageRepository = mock<CredentialsStorageRepository>()
    private val mockEncodeCredentialsUseCase = mock<EncodeCredentialsUseCase>()

    private val useCase = PerformAuthenticationUseCase(mockAuthenticationRepository, mockCredentialsStorageRepository, mockEncodeCredentialsUseCase)

    @Test
    @DisplayName(
        """
        Given authentication fails with 'INVALID_CREDENTIALS'
        When performing authentication
        The expect a failure with reason 'INVALID_CREDENTIALS'
    """
    )
    internal fun testAuthenticationFails(scope: TestCoroutineScope) = scope.runBlockingTest {
        val exception = ApplicationException(AuthenticationFailureReason.INVALID_CREDENTIALS)
        whenever(mockAuthenticationRepository.authenticate(STUB_USER_NAME, STUB_PASSWORD)).doAnswer { throw exception }

        consumingFow(limit = 2) { useCase.execute(STUB_USER_NAME, STUB_PASSWORD) }
            .hasData()
            .all {
                hasSize(2)
                hasItemAtPosition(0).isEqualTo(AsyncResult.Loading)
                hasItemAtPosition(1).isEqualTo(AsyncResult.Failure(exception))
            }
    }

    @Test
    @DisplayName(
        """
        Given authentication succeeds
        When performing authentication
        The expect success
    """
    )
    internal fun testAuthenticationSucceeds(scope: TestCoroutineScope) = scope.runBlockingTest {
        whenever(mockAuthenticationRepository.authenticate(STUB_USER_NAME, STUB_PASSWORD)).doReturn(Unit)
        whenever(mockEncodeCredentialsUseCase.execute(STUB_USER_NAME, STUB_PASSWORD)).doReturn(STUB_CREDENTIALS)
        whenever(mockCredentialsStorageRepository.store(STUB_CREDENTIALS)).doReturn(Unit)

        consumingFow(limit = 2) { useCase.execute(STUB_USER_NAME, STUB_PASSWORD) }
            .hasData()
            .all {
                hasSize(2)
                hasItemAtPosition(0).isEqualTo(AsyncResult.Loading)
                hasItemAtPosition(1).isEqualTo(AsyncResult.Success(Unit))
            }
    }
}
