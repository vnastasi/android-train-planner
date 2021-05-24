package md.vnastasi.trainplanner.dashboard.planner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import md.vnastasi.trainplanner.R
import md.vnastasi.trainplanner.dashboard.DashboardFragmentDirections
import md.vnastasi.trainplanner.databinding.FragmentTripPlannerBinding

class TripPlannerFragment : Fragment() {

    private var _viewBinding: FragmentTripPlannerBinding? = null
    private val viewBinding: FragmentTripPlannerBinding get() = requireNotNull(_viewBinding)

    private val mainNvaController: NavController
        get() = requireActivity().findNavController(R.id.main_fragment_container)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _viewBinding = FragmentTripPlannerBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val departureStationInputCLickListener : (View) -> Unit = {
            val navAction = DashboardFragmentDirections.actionDashboardToStationSearch("0001")
            mainNvaController.navigate(navAction)
        }
        viewBinding.inputDepartureStation.setOnClickListener(departureStationInputCLickListener)
        viewBinding.inputDepartureStation.editText?.setOnClickListener(departureStationInputCLickListener)

        val arrivalStationInputCLickListener : (View) -> Unit = {
            val navAction = DashboardFragmentDirections.actionDashboardToStationSearch("0002")
            mainNvaController.navigate(navAction)
        }
        viewBinding.inputArrivalStation.setOnClickListener(arrivalStationInputCLickListener)
        viewBinding.inputArrivalStation.editText?.setOnClickListener(arrivalStationInputCLickListener)
    }

    override fun onDestroyView() {
        _viewBinding = null
        super.onDestroyView()
    }
}
