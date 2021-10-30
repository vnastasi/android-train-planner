package md.vnastasi.trainplanner.login.repository

import kotlinx.coroutines.delay
import md.vnastasi.trainplanner.BuildConfig
import md.vnastasi.trainplanner.exception.ApplicationException

class LocalAuthenticationRepositoryImpl : AuthenticationRepository {

    override suspend fun authenticate(userName: String, password: String) {
        delay(1000L) // Simulate network request
        when {
            userName.isEmpty() -> throw ApplicationException(AuthenticationFailureReason.EMPTY_USER_NAME)
            password.isEmpty() -> throw ApplicationException(AuthenticationFailureReason.EMPTY_PASSWORD)
            userName != BuildConfig.APP_USER || password != BuildConfig.APP_PASSWORD -> throw ApplicationException(AuthenticationFailureReason.INVALID_CREDENTIALS)
            else -> Unit
        }
    }
}
