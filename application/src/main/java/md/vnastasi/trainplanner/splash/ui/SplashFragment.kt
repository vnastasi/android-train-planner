package md.vnastasi.trainplanner.splash.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import kotlinx.coroutines.flow.collect
import md.vnastasi.trainplanner.R
import md.vnastasi.trainplanner.databinding.FragmentSplashBinding
import md.vnastasi.trainplanner.ui.providingViewModels
import md.vnastasi.trainplanner.ui.whileStarted

class SplashFragment(
    private val viewModelProvider: SplashViewModel.Provider
) : Fragment() {

    private val viewModel by providingViewModels { viewModelProvider.provide() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        FragmentSplashBinding.inflate(layoutInflater, container, false).root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        whileStarted {
            viewModel.authenticationState.collect(::onAuthenticationStateChanged)
        }
    }

    private fun onAuthenticationStateChanged(state: AuthenticationState) {
        val navigationOptions = navOptions {
            popUpTo(R.id.splash) { inclusive = true }
        }
        when (state) {
            is AuthenticationState.Authenticated -> findNavController().navigate(SplashFragmentDirections.actionSplashToDashboard(), navigationOptions)
            is AuthenticationState.Anonymous -> findNavController().navigate(SplashFragmentDirections.actionSplashToLogin(), navigationOptions)
            else -> Unit
        }
    }
}
