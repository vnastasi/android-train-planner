package md.vnastasi.trainplanner.splash.ui

import androidx.annotation.MainThread
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import md.vnastasi.trainplanner.async.Event
import md.vnastasi.trainplanner.splash.nav.SplashNavigationRoute
import md.vnastasi.trainplanner.splash.usecase.CheckCredentialsUseCase

class MockSplashViewModel {

    private val _navigationRoute: MutableSharedFlow<Event<SplashNavigationRoute>> = MutableSharedFlow(replay = 1, extraBufferCapacity = 0, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    private val mockCheckCredentialsUseCase = mock<CheckCredentialsUseCase> {
        onBlocking { execute() } doReturn false
    }

    val instance = spy(SplashViewModel(mockCheckCredentialsUseCase))
    val provider = mock<SplashViewModel.Provider>()

    init {
        doReturn(_navigationRoute).whenever(instance).navigationRoute
        doReturn(instance).whenever(provider).provide()
    }

    @MainThread
    fun expectNavigationRoute(route: SplashNavigationRoute) {
        _navigationRoute.tryEmit(Event(route))
    }
}
