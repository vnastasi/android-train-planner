package md.vnastasi.trainplanner.splash.ui

sealed class AuthenticationState {

    object Pending : AuthenticationState()

    object Authenticated: AuthenticationState()

    object Anonymous: AuthenticationState()
}
