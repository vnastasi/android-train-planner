package md.vnastasi.trainplanner.login.ui

import md.vnastasi.trainplanner.exception.FailureReason

sealed class LoginViewState {

    object Init : LoginViewState()

    object Authenticated : LoginViewState()

    data class AuthenticationFailed(val reason: FailureReason): LoginViewState()

    object AuthenticationInProgress : LoginViewState()
}
