package md.vnastasi.trainplanner.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import md.vnastasi.trainplanner.R
import md.vnastasi.trainplanner.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private var _viewBinding: FragmentDashboardBinding? = null
    private val viewBinding: FragmentDashboardBinding get() = requireNotNull(_viewBinding)

    private val dashboardNavController: NavController
        get() = requireNotNull(childFragmentManager.findFragmentById(R.id.dashboard_fragment_container)).findNavController()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _viewBinding = FragmentDashboardBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewBinding.dashboardNavView.setupWithNavController(dashboardNavController)
    }

    override fun onDestroyView() {
        _viewBinding = null
        super.onDestroyView()
    }
}
