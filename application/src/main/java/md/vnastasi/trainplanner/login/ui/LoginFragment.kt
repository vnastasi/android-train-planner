package md.vnastasi.trainplanner.login.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import md.vnastasi.trainplanner.R
import md.vnastasi.trainplanner.async.Event
import md.vnastasi.trainplanner.databinding.FragmentLoginBinding
import md.vnastasi.trainplanner.exception.FailureReason
import md.vnastasi.trainplanner.login.nav.LoginNavigationRoute
import md.vnastasi.trainplanner.login.repository.AuthenticationFailureReason
import md.vnastasi.trainplanner.ui.providingViewModels

class LoginFragment(
    private val viewModelProvider: LoginViewModel.Provider
) : Fragment() {

    private var _viewBinding: FragmentLoginBinding? = null
    private val viewBinding: FragmentLoginBinding get() = requireNotNull(_viewBinding)

    private val viewModel by providingViewModels { viewModelProvider.provide() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        FragmentLoginBinding.inflate(inflater, container, false).also { _viewBinding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewBinding.btnLogin.setOnClickListener {
            removeAllErrors()
            viewModel.onLogin(
                userName = viewBinding.inputUserName.editText?.text?.toString().orEmpty(),
                password = viewBinding.inputPassword.editText?.text?.toString().orEmpty()
            )
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewState.collect(::onViewStateChanged)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.navigationRoute.collect(::onNavigationRouteChanged)
        }
    }

    private fun removeAllErrors() {
        viewBinding.inputUserName.error = null
        viewBinding.inputPassword.error = null
    }

    private fun onViewStateChanged(viewState: LoginViewState) {
        when (viewState) {
            is LoginViewState.Authenticated -> {
                Toast.makeText(requireContext(), R.string.toast_message_login_success, Toast.LENGTH_LONG).show()
                viewModel.navigateToDashboard()
            }
            is LoginViewState.AuthenticationFailed -> renderErrors(viewState.reason)
            else -> Unit
        }
    }

    private fun onNavigationRouteChanged(event: Event<LoginNavigationRoute>) {
        val route = event.consume() ?: return

        val navigationOptions = navOptions {
            popUpTo(R.id.login) { inclusive = true }
        }
        val action = when (route) {
            is LoginNavigationRoute.Dashboard -> LoginFragmentDirections.actionLoginToDashboard()
        }

        findNavController().navigate(action, navigationOptions)
    }

    private fun renderErrors(failureReason: FailureReason) {
        when (failureReason) {
            AuthenticationFailureReason.EMPTY_USER_NAME -> viewBinding.inputUserName.error = failureReason.message
            AuthenticationFailureReason.EMPTY_PASSWORD -> viewBinding.inputPassword.error = failureReason.message
            AuthenticationFailureReason.INVALID_CREDENTIALS -> viewBinding.inputPassword.error = failureReason.message
            else -> Snackbar.make(requireView(), failureReason.message, Snackbar.LENGTH_LONG).show()
        }
    }

    override fun onDestroyView() {
        _viewBinding = null
        super.onDestroyView()
    }
}
