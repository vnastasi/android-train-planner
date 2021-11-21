package md.vnastasi.trainplanner.login.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import md.vnastasi.trainplanner.async.AsyncResult
import md.vnastasi.trainplanner.async.DispatcherRegistry
import md.vnastasi.trainplanner.login.usecase.PerformAuthenticationUseCase
import md.vnastasi.trainplanner.open.OpenForTesting

@OpenForTesting
class LoginViewModel(
    private val performAuthenticationUseCase: PerformAuthenticationUseCase
) : ViewModel() {

    private val _viewState: MutableStateFlow<LoginUiStateModel> = MutableStateFlow(LoginUiStateModel.Pending)
    val viewState: StateFlow<LoginUiStateModel> = _viewState

    fun onLogin(userName: String, password: String) {
        viewModelScope.launch(DispatcherRegistry.Main) {
            performAuthenticationUseCase.execute(userName, password)
                .map { result ->
                    when (result) {
                        is AsyncResult.Loading -> LoginUiStateModel.AuthenticationInProgress
                        is AsyncResult.Success -> LoginUiStateModel.Authenticated
                        is AsyncResult.Failure -> LoginUiStateModel.AuthenticationFailed(result.exception.failureReason)
                    }
                }
                .flowOn(DispatcherRegistry.IO)
                .distinctUntilChanged()
                .collect { _viewState.value = it }
        }
    }

    @OpenForTesting
    class Provider(
        private val performAuthenticationUseCase: PerformAuthenticationUseCase
    ) {

        fun provide(): LoginViewModel = LoginViewModel(performAuthenticationUseCase)
    }
}
