package md.vnastasi.trainplanner.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                showExitConfirmationAlert()
            }
        })
    }

    private fun showExitConfirmationAlert() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.alert_confirm_exit_title)
            .setMessage(R.string.alert_confirm_exit_message)
            .setNegativeButton(R.string.alert_confirm_exit_no) { dialog, _ -> dialog.dismiss() }
            .setPositiveButton(R.string.alert_confirm_exit_yes) { _, _ -> requireActivity().finish() }
            .show()
    }

    override fun onDestroyView() {
        _viewBinding = null
        super.onDestroyView()
    }
}
