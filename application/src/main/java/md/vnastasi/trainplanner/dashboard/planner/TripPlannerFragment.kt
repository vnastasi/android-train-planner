package md.vnastasi.trainplanner.dashboard.planner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import md.vnastasi.trainplanner.databinding.FragmentTripPlannerBinding

class TripPlannerFragment : Fragment() {

    private var _viewBinding: FragmentTripPlannerBinding? = null
    private val viewBinding: FragmentTripPlannerBinding get() = requireNotNull(_viewBinding)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _viewBinding = FragmentTripPlannerBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onDestroyView() {
        _viewBinding = null
        super.onDestroyView()
    }
}
