package md.vnastasi.trainplanner.splash.ui

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import md.vnastasi.trainplanner.R
import md.vnastasi.trainplanner.splash.nav.SplashNavigationRoute
import md.vnastasi.trainplanner.util.nav.replaceNavController
import md.vnastasi.trainplanner.util.nav.verifyCurrentDestination
import org.junit.Test

class SplashFragmentTest {

    private val mockSplashViewModel = MockSplashViewModel()

    private val navController = TestNavHostController(ApplicationProvider.getApplicationContext())

    @Test
    fun authenticatedStateNavigatesToDashboard() {
        launchFragment().onFragment {
            mockSplashViewModel.expectNavigationRoute(SplashNavigationRoute.Dashboard)
        }

        navController.verifyCurrentDestination(R.id.dashboard)
    }

    @Test
    fun unauthenticatedStateNavigatesToLogin() {
        launchFragment().onFragment {
            mockSplashViewModel.expectNavigationRoute(SplashNavigationRoute.Login)
        }

        navController.verifyCurrentDestination(R.id.login)
    }

    private fun launchFragment() = launchFragmentInContainer(themeResId = R.style.Theme_TrainPlanner) {
        SplashFragment(mockSplashViewModel.provider).also { it.replaceNavController(navController, R.navigation.main_navigation_graph, R.id.splash) }
    }
}