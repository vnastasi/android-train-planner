package md.vnastasi.trainplanner.splash.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import md.vnastasi.trainplanner.async.DispatcherRegistry
import md.vnastasi.trainplanner.splash.usecase.CheckCredentialsUseCase

class SplashViewModel(
    private val checkCredentialsUseCase: CheckCredentialsUseCase
) : ViewModel() {

    private val _authenticationState: MutableStateFlow<AuthenticationState> = MutableStateFlow(AuthenticationState.Pending)
    val authenticationState: StateFlow<AuthenticationState> = _authenticationState

    init {
        viewModelScope.launch(DispatcherRegistry.Main) {
            _authenticationState.value = withContext(DispatcherRegistry.Default) {
                delay(5000L)
                if (checkCredentialsUseCase.execute()) AuthenticationState.Authenticated else AuthenticationState.Anonymous
            }
        }
    }

    class Provider(
        private val checkCredentialsUseCase: CheckCredentialsUseCase
    ) {

        fun provide(): SplashViewModel = SplashViewModel(checkCredentialsUseCase)
    }
}
