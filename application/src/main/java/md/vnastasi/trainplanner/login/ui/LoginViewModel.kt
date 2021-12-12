package md.vnastasi.trainplanner.login.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import md.vnastasi.trainplanner.async.AsyncResult
import md.vnastasi.trainplanner.async.DispatcherRegistry
import md.vnastasi.trainplanner.async.Event
import md.vnastasi.trainplanner.login.nav.LoginNavigationRoute
import md.vnastasi.trainplanner.login.usecase.PerformAuthenticationUseCase
import md.vnastasi.trainplanner.open.OpenForTesting

@OpenForTesting
class LoginViewModel(
    private val performAuthenticationUseCase: PerformAuthenticationUseCase
) : ViewModel() {

    private val _viewState: MutableStateFlow<LoginViewState> = MutableStateFlow(LoginViewState.Init)
    val viewState: Flow<LoginViewState> = _viewState.asStateFlow()

    private val _navigationRoute = MutableSharedFlow<Event<LoginNavigationRoute>>(replay = 1, extraBufferCapacity = 0, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val navigationRoute: Flow<Event<LoginNavigationRoute>> = _navigationRoute.asSharedFlow().distinctUntilChanged()

    fun onLogin(userName: String, password: String) {
        viewModelScope.launch(DispatcherRegistry.Main) {
            performAuthenticationUseCase.execute(userName, password)
                .map { result ->
                    when (result) {
                        is AsyncResult.Loading -> LoginViewState.AuthenticationInProgress
                        is AsyncResult.Success -> LoginViewState.Authenticated
                        is AsyncResult.Failure -> LoginViewState.AuthenticationFailed(result.exception.failureReason)
                    }
                }
                .flowOn(DispatcherRegistry.IO)
                .distinctUntilChanged()
                .collect { _viewState.value = it }
        }
    }

    fun navigateToDashboard() {
        viewModelScope.launch(DispatcherRegistry.Main) {
            _navigationRoute.emit(Event(LoginNavigationRoute.Dashboard))
        }
    }

    @OpenForTesting
    class Provider(
        private val performAuthenticationUseCase: PerformAuthenticationUseCase
    ) {

        fun provide(): LoginViewModel = LoginViewModel(performAuthenticationUseCase)
    }
}
