package md.vnastasi.trainplanner.dashboard.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import md.vnastasi.trainplanner.R
import md.vnastasi.trainplanner.dashboard.DashboardFragmentDirections

class MapFragment : Fragment() {

    private val mainNvaController: NavController
        get() = requireActivity().findNavController(R.id.main_fragment_container)

    private val callback = OnMapReadyCallback { googleMap ->
        val sydney = LatLng(-34.0, 151.0)
        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_maps, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map_fragment_container) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    private fun onMarkerClicked() {
        val navDirection = DashboardFragmentDirections.actionDashboardToTimetable("0000")
        mainNvaController.navigate(navDirection)
    }
}
