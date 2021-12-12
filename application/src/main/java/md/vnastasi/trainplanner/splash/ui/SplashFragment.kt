package md.vnastasi.trainplanner.splash.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import kotlinx.coroutines.flow.collect
import md.vnastasi.trainplanner.R
import md.vnastasi.trainplanner.async.Event
import md.vnastasi.trainplanner.databinding.FragmentSplashBinding
import md.vnastasi.trainplanner.splash.nav.SplashNavigationRoute
import md.vnastasi.trainplanner.ui.providingViewModels

class SplashFragment(
    private val viewModelProvider: SplashViewModel.Provider
) : Fragment() {

    private val viewModel by providingViewModels { viewModelProvider.provide() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        FragmentSplashBinding.inflate(layoutInflater, container, false).root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.navigationRoute.collect(::onNavigationRouteChanged)
        }
    }

    private fun onNavigationRouteChanged(event: Event<SplashNavigationRoute>) {
        val route = event.consume() ?: return

        val navigationOptions = navOptions {
            popUpTo(R.id.splash) { inclusive = true }
        }
        val action = when (route) {
            is SplashNavigationRoute.Dashboard -> SplashFragmentDirections.actionSplashToDashboard()
            is SplashNavigationRoute.Login -> SplashFragmentDirections.actionSplashToLogin()
        }

        findNavController().navigate(action, navigationOptions)
    }
}
