package md.vnastasi.trainplanner.login.repository

import assertk.assertThat
import assertk.assertions.*
import kotlinx.coroutines.test.runBlockingTest
import md.vnastasi.trainplanner.BuildConfig
import md.vnastasi.trainplanner.exception.ApplicationException
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

internal class LocalAuthenticationRepositoryImplTest {

    private val repository = LocalAuthenticationRepositoryImpl()

    @Test
    @DisplayName(
        """
        Given an empty user name
        When authenticating
        Then expect exception to be raised with reason 'EMPTY_USER_NAME'
    """
    )
    internal fun testMissingUserName() = runBlockingTest {
        assertThat { repository.authenticate("", "password") }
            .isFailure()
            .isInstanceOf(ApplicationException::class)
            .prop("code") { it.failureReason.code }.isEqualTo("EMPTY_USER_NAME")
    }

    @Test
    @DisplayName(
        """
        Given an empty password
        When authenticating
        Then expect exception to be raised with reason 'EMPTY_PASSWORD'
    """
    )
    internal fun testMissingUserPassword() = runBlockingTest {
        assertThat { repository.authenticate("user", "") }
            .isFailure()
            .isInstanceOf(ApplicationException::class)
            .prop("code") { it.failureReason.code }.isEqualTo("EMPTY_PASSWORD")
    }

    @Test
    @DisplayName(
        """
        Given an invalid combination of user name and password
        When authenticating
        Then expect exception to be raised with reason 'INVALID_CREDENTIALS'
    """
    )
    internal fun testInvalidCredentials() = runBlockingTest {
        assertThat { repository.authenticate("a", "b") }
            .isFailure()
            .isInstanceOf(ApplicationException::class)
            .prop("code") { it.failureReason.code }.isEqualTo("INVALID_CREDENTIALS")
    }

    @Test
    @DisplayName(
        """
        Given a valid combination of user name and password
        When authenticating
        Then expect no exception to be raised
    """
    )
    internal fun testValidCredentials() = runBlockingTest {
        assertThat { repository.authenticate(BuildConfig.APP_USER, BuildConfig.APP_PASSWORD) }
            .isSuccess()
    }
}
