package md.vnastasi.trainplanner.splash.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import md.vnastasi.trainplanner.async.*
import md.vnastasi.trainplanner.splash.nav.SplashNavigationRoute
import md.vnastasi.trainplanner.splash.usecase.CheckCredentialsUseCase

class SplashViewModel(
    private val checkCredentialsUseCase: CheckCredentialsUseCase
) : ViewModel() {

    private val _navigationRoute = MutableSharedFlow<Event<SplashNavigationRoute>>(replay = 1, extraBufferCapacity = 0, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val navigationRoute: Flow<Event<SplashNavigationRoute>> = _navigationRoute.asSharedFlow().distinctUntilChanged()

    init {
        checkAuthenticationStatus()
    }

    private fun checkAuthenticationStatus() {
        viewModelScope.launch(DispatcherRegistry.Main) {
            val isAuthenticated = withContext(DispatcherRegistry.Default) {
                delay(2000L)
                checkCredentialsUseCase.execute()
            }
            val route = if (isAuthenticated) SplashNavigationRoute.Dashboard else SplashNavigationRoute.Login
            _navigationRoute.emit(Event(route))
        }
    }

    class Provider(
        private val checkCredentialsUseCase: CheckCredentialsUseCase
    ) {

        fun provide(): SplashViewModel = SplashViewModel(checkCredentialsUseCase)
    }
}
