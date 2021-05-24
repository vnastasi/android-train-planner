package md.vnastasi.trainplanner.station

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import md.vnastasi.trainplanner.databinding.FragmentStationSearchBinding

class StationSearchFragment : Fragment() {

    private var _viewBinding: FragmentStationSearchBinding? = null
    private val viewBinding: FragmentStationSearchBinding get() = requireNotNull(_viewBinding)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _viewBinding = FragmentStationSearchBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onDestroyView() {
        _viewBinding = null
        super.onDestroyView()
    }
}
