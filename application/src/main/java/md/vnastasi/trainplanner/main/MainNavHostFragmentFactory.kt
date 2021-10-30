package md.vnastasi.trainplanner.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import md.vnastasi.trainplanner.login.ui.LoginFragment
import md.vnastasi.trainplanner.login.ui.LoginViewModel
import md.vnastasi.trainplanner.splash.ui.SplashFragment
import md.vnastasi.trainplanner.splash.ui.SplashViewModel

class MainNavHostFragmentFactory(
    private val splashViewModelProvider: SplashViewModel.Provider,
    private val loginViewModelProvider: LoginViewModel.Provider
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment =
        when (loadFragmentClass(classLoader, className)) {
            SplashFragment::class.java -> SplashFragment(splashViewModelProvider)
            LoginFragment::class.java -> LoginFragment(loginViewModelProvider)
            else -> super.instantiate(classLoader, className)
        }
}
