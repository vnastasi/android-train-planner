package md.vnastasi.trainplanner.timetable

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import md.vnastasi.trainplanner.databinding.FragmentTimetableBinding

class TimetableFragment : Fragment() {

    private var _viewBinding: FragmentTimetableBinding? = null
    private val viewBinding: FragmentTimetableBinding get() = requireNotNull(_viewBinding)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _viewBinding = FragmentTimetableBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onDestroyView() {
        _viewBinding = null
        super.onDestroyView()
    }
}
