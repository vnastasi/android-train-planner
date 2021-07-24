package md.vnastasi.trainplanner.login.ui

import md.vnastasi.trainplanner.exception.FailureReason

sealed class LoginUiStateModel {

    object AuthenticationRequired : LoginUiStateModel()

    object Authenticated : LoginUiStateModel()

    data class AuthenticationFailed(val reason: FailureReason): LoginUiStateModel()

    object AuthenticationInProgress : LoginUiStateModel()
}
