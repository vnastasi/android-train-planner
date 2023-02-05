package md.vnastasi.trainplanner.dashboard.disruption.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch
import md.vnastasi.trainplanner.async.AsyncResult
import md.vnastasi.trainplanner.databinding.FragmentDisruptionListBinding
import md.vnastasi.trainplanner.ui.providingViewModels

class DisruptionListFragment(
    viewModelProvider: DisruptionListViewModel.Provider
) : Fragment() {

    private var _viewBinding: FragmentDisruptionListBinding? = null
    private val viewBinding: FragmentDisruptionListBinding get() = requireNotNull(_viewBinding)

    private val viewModel by providingViewModels(viewModelProvider::provideFor)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _viewBinding = FragmentDisruptionListBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewState.collect(::onViewStateChanged)
            }
        }
    }

    override fun onDestroyView() {
        _viewBinding = null
        super.onDestroyView()
    }

    private fun onViewStateChanged(viewState: AsyncResult<List<DisruptionListItem>>) {
        when (viewState) {
            is AsyncResult.Loading -> Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
            is AsyncResult.Failure -> Toast.makeText(requireContext(), "Error: ${viewState.exception.failureReason.message}", Toast.LENGTH_LONG).show()
            is AsyncResult.Success -> Unit
        }
    }
}
