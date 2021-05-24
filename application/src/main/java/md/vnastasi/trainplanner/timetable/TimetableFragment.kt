package md.vnastasi.trainplanner.timetable

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import md.vnastasi.trainplanner.R
import md.vnastasi.trainplanner.databinding.FragmentTimetableBinding

class TimetableFragment : Fragment() {

    private val arguments by navArgs<TimetableFragmentArgs>()

    private var _viewBinding: FragmentTimetableBinding? = null
    private val viewBinding: FragmentTimetableBinding get() = requireNotNull(_viewBinding)

    private val mainNvaController: NavController
        get() = requireActivity().findNavController(R.id.main_fragment_container)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _viewBinding = FragmentTimetableBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments.stationId.also { Log.d("TIMETABLE", "stationId = $it") }
        viewBinding.toolbar.setupWithNavController(mainNvaController)
    }

    override fun onDestroyView() {
        _viewBinding = null
        super.onDestroyView()
    }
}
