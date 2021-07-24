package md.vnastasi.trainplanner.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import md.vnastasi.trainplanner.login.ui.LoginFragment
import md.vnastasi.trainplanner.login.ui.LoginViewModel

class MainNavHostFragmentFactory(
    private val loginViewModelProvider: LoginViewModel.Provider
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment =
        when (loadFragmentClass(classLoader, className)) {
            LoginFragment::class.java -> LoginFragment(loginViewModelProvider)
            else -> super.instantiate(classLoader, className)
        }
}
