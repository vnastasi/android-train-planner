package md.vnastasi.trainplanner.login.ui

import androidx.annotation.MainThread
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import md.vnastasi.trainplanner.async.Event
import md.vnastasi.trainplanner.login.nav.LoginNavigationRoute

class MockLoginViewModel {

    private val _viewState: MutableStateFlow<LoginViewState> = MutableStateFlow(LoginViewState.Init)
    private val _navigationRoute: MutableSharedFlow<Event<LoginNavigationRoute>> = MutableSharedFlow(replay = 1, extraBufferCapacity = 0, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    val instance = spy(LoginViewModel(mock()))
    val provider = mock<LoginViewModel.Provider>()

    init {
        doReturn(_viewState).whenever(instance).viewState
        doReturn(_navigationRoute).whenever(instance).navigationRoute
        doNothing().whenever(instance).onLogin(any(), any())
        doReturn(instance).whenever(provider).provide()
    }

    @MainThread
    fun setViewState(state: LoginViewState) {
        _viewState.value = state
    }

    @MainThread
    fun expectNavigationRoute(route: LoginNavigationRoute) {
        _navigationRoute.tryEmit(Event(route))
    }
}
