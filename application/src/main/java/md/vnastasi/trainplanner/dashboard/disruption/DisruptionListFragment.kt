package md.vnastasi.trainplanner.dashboard.disruption

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import md.vnastasi.trainplanner.databinding.FragmentDisruptionListBinding

class DisruptionListFragment : Fragment() {

    private var _viewBinding: FragmentDisruptionListBinding? = null
    private val viewBinding: FragmentDisruptionListBinding get() = requireNotNull(_viewBinding)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _viewBinding = FragmentDisruptionListBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onDestroyView() {
        _viewBinding = null
        super.onDestroyView()
    }
}
