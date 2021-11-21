package md.vnastasi.trainplanner.login.ui

import androidx.annotation.MainThread
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.flow.MutableStateFlow

class MockLoginViewModel {

    private val _viewState: MutableStateFlow<LoginUiStateModel> = MutableStateFlow(LoginUiStateModel.Pending)

    val instance = spy(LoginViewModel(mock()))
    val provider = mock<LoginViewModel.Provider>()

    init {
        doReturn(_viewState).whenever(instance).viewState
        doNothing().whenever(instance).onLogin(any(), any())
        doReturn(instance).whenever(provider).provide()
    }

    @MainThread
    fun setViewState(state: LoginUiStateModel) {
        _viewState.value = state
    }
}
