package md.vnastasi.trainplanner.login.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect
import md.vnastasi.trainplanner.R
import md.vnastasi.trainplanner.databinding.FragmentLoginBinding
import md.vnastasi.trainplanner.exception.FailureReason
import md.vnastasi.trainplanner.login.repository.AuthenticationFailureReason
import md.vnastasi.trainplanner.ui.providingActivityViewModels
import md.vnastasi.trainplanner.ui.whileStarted

class LoginFragment(viewModelProvider: LoginViewModel.Provider) : Fragment() {

    private var _viewBinding: FragmentLoginBinding? = null
    private val viewBinding: FragmentLoginBinding get() = requireNotNull(_viewBinding)

    private val viewModel by providingActivityViewModels { viewModelProvider.provide() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _viewBinding = FragmentLoginBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewBinding.btnLogin.setOnClickListener {
            removeAllErrors()
            viewModel.onLogin(
                userName = viewBinding.inputUserName.editText?.text?.toString().orEmpty(),
                password = viewBinding.inputPassword.editText?.text?.toString().orEmpty()
            )
        }

        whileStarted {
            viewModel.viewState.collect(::onViewStateChanged)
        }
    }

    private fun removeAllErrors() {
        viewBinding.inputUserName.error = null
        viewBinding.inputPassword.error = null
    }

    private fun onViewStateChanged(viewState: LoginUiStateModel) {
        when (viewState) {
            is LoginUiStateModel.AuthenticationInProgress -> Unit
            is LoginUiStateModel.AuthenticationRequired -> Unit
            is LoginUiStateModel.Authenticated -> requireParentFragment().findNavController().navigate(R.id.dashboard)
            is LoginUiStateModel.AuthenticationFailed -> renderErrors(viewState.reason)
        }
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
