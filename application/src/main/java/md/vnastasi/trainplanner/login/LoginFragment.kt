package md.vnastasi.trainplanner.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import md.vnastasi.trainplanner.R
import md.vnastasi.trainplanner.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private var _viewBinding: FragmentLoginBinding? = null
    private val viewBinding: FragmentLoginBinding get() = requireNotNull(_viewBinding)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _viewBinding = FragmentLoginBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewBinding.btnLogin.setOnClickListener { requireParentFragment().findNavController().navigate(R.id.dashboard) }
    }

    override fun onDestroyView() {
        _viewBinding = null
        super.onDestroyView()
    }
}
