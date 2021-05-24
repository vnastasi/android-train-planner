package md.vnastasi.trainplanner.station

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
import md.vnastasi.trainplanner.databinding.FragmentStationSearchBinding

class StationSearchFragment : Fragment() {

    private val arguments by navArgs<StationSearchFragmentArgs>()

    private var _viewBinding: FragmentStationSearchBinding? = null
    private val viewBinding: FragmentStationSearchBinding get() = requireNotNull(_viewBinding)

    private val mainNvaController: NavController
        get() = requireActivity().findNavController(R.id.main_fragment_container)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _viewBinding = FragmentStationSearchBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments.preselectedStationId.also { Log.d("STATION_SEARCH", "preselectedStationId = $it") }
        viewBinding.toolbar.setupWithNavController(mainNvaController)
        viewBinding.toolbar.title = "Search"
    }

    override fun onDestroyView() {
        _viewBinding = null
        super.onDestroyView()
    }
}
