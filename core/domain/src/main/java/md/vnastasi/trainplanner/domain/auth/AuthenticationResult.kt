package md.vnastasi.trainplanner.domain.auth

sealed class AuthenticationResult {

    object Success : AuthenticationResult()

    data class Fail(val reason: FailReason) : AuthenticationResult()

    enum class FailReason {

        MISSING_USERNAME, MISSING_PASSWORD, BAD_CREDENTIALS
    }

    companion object {

        fun success(): AuthenticationResult = Success

        fun fail(reason: FailReason): AuthenticationResult = Fail(reason)
    }
}
