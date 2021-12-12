package md.vnastasi.trainplanner.login.ui

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.verify
import md.vnastasi.trainplanner.R
import md.vnastasi.trainplanner.login.nav.LoginNavigationRoute
import md.vnastasi.trainplanner.login.repository.AuthenticationFailureReason
import md.vnastasi.trainplanner.util.nav.replaceNavController
import md.vnastasi.trainplanner.util.nav.verifyCurrentDestination
import org.junit.Test

class LoginFragmentTest {

    private val mockLoginViewModel = MockLoginViewModel()
    private val navController = TestNavHostController(ApplicationProvider.getApplicationContext())

    @Test
    fun emptyUserNameFailureDisplaysErrorTextOnUserNameInput() {
        launchFragment().onFragment {
            mockLoginViewModel.setViewState(LoginViewState.AuthenticationFailed(AuthenticationFailureReason.EMPTY_USER_NAME))
        }

        loginScreen {
            hasNoPasswordError()
            hasUserNameError(AuthenticationFailureReason.EMPTY_USER_NAME.message)
        }
    }

    @Test
    fun emptyPasswordFailureDisplaysErrorTextOnPasswordInput() {
        launchFragment().onFragment {
            mockLoginViewModel.setViewState(LoginViewState.AuthenticationFailed(AuthenticationFailureReason.EMPTY_PASSWORD))
        }

        loginScreen {
            hasNoUserNameError()
            hasPasswordError(AuthenticationFailureReason.EMPTY_PASSWORD.message)
        }
    }

    @Test
    fun invalidCredentialsFailureDisplaysErrorTextOnPasswordInput() {
        launchFragment().onFragment {
            mockLoginViewModel.setViewState(LoginViewState.AuthenticationFailed(AuthenticationFailureReason.INVALID_CREDENTIALS))
        }

        loginScreen {
            hasNoUserNameError()
            hasPasswordError(AuthenticationFailureReason.INVALID_CREDENTIALS.message)
        }
    }

    @Test
    fun clickOnLoginButtonTriggersViewModel() {
        val userName = "User name"
        val password = "Password"

        launchFragment()

        loginScreen {
            typeUserName(userName)
            typePassword(password)
            hasNoUserNameError()
            hasNoPasswordError()
            clickOnLogin()
        }

        verify(mockLoginViewModel.instance).onLogin(eq(userName), eq(password))
    }

    @Test
    fun successAuthenticationTriggersNavigationToDashboard() {
        val fragmentScenario = launchFragment()

        fragmentScenario.onFragment {
            mockLoginViewModel.setViewState(LoginViewState.Authenticated)
        }

        verify(mockLoginViewModel.instance).navigateToDashboard()

        fragmentScenario.onFragment {
            mockLoginViewModel.expectNavigationRoute(LoginNavigationRoute.Dashboard)
        }

        navController.verifyCurrentDestination(R.id.dashboard)
    }

    private fun launchFragment() = launchFragmentInContainer(themeResId = R.style.Theme_TrainPlanner) {
        LoginFragment(mockLoginViewModel.provider).also { it.replaceNavController(navController, R.navigation.main_navigation_graph, R.id.login) }
    }
}
