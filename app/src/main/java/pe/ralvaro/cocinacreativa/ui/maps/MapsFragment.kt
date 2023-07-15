package pe.ralvaro.cocinacreativa.ui.maps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import pe.ralvaro.cocinacreativa.R
import pe.ralvaro.cocinacreativa.util.generateMarker

@AndroidEntryPoint
class MapsFragment : Fragment() {

    private val args: MapsFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?

        val smallMarkerIcon = requireContext().generateMarker(args.placeName)
        mapFragment?.getMapAsync {
            val location = LatLng(args.latValue.toDouble(), args.lonValue.toDouble())
            it.addMarker(MarkerOptions().position(location).icon(smallMarkerIcon))
            it.moveCamera(CameraUpdateFactory.newLatLngZoom(location, resources.getDimension(R.dimen.zoom_dimension_complete)))
        }
    }

}