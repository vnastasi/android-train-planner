package md.vnastasi.trainplanner.splash.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
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
        when (state) {
            is AuthenticationState.Authenticated -> requireParentFragment().findNavController().navigate(R.id.action_splash_to_dashboard)
            is AuthenticationState.Anonymous -> requireParentFragment().findNavController().navigate(R.id.action_splash_to_login)
            else -> Unit
        }
    }
}
