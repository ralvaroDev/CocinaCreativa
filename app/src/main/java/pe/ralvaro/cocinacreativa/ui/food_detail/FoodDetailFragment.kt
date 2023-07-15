package pe.ralvaro.cocinacreativa.ui.food_detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import coil.Coil
import coil.load
import coil.transform.BlurTransformation
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.ktx.addMarker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import pe.ralvaro.cocinacreativa.R
import pe.ralvaro.cocinacreativa.data.model.FoodDish
import pe.ralvaro.cocinacreativa.databinding.FragmentFoodDetailBinding
import pe.ralvaro.cocinacreativa.ui.BaseFragment
import pe.ralvaro.cocinacreativa.util.Result
import pe.ralvaro.cocinacreativa.util.applyIfNotNull
import pe.ralvaro.cocinacreativa.util.generateMarker
import pe.ralvaro.cocinacreativa.util.ignoreException
import pe.ralvaro.cocinacreativa.util.launchAndRepeatWithViewLifecycle
import timber.log.Timber

@AndroidEntryPoint
class FoodDetailFragment :
    BaseFragment<FragmentFoodDetailBinding>(FragmentFoodDetailBinding::inflate) {

    private val foodDetailViewModel: FoodDetailViewModel by viewModels()

    private var mMap: MapView? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mMap?.applyIfNotNull { onCreate(savedInstanceState) }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        mMap?.applyIfNotNull { onSaveInstanceState(outState) }
        super.onSaveInstanceState(outState)
    }

    override fun onResume() {
        super.onResume()
        mMap?.applyIfNotNull { onResume() }
    }

    override fun onStart() {
        super.onStart()
        mMap?.applyIfNotNull { onStart() }
    }

    override fun onStop() {
        super.onStop()
        mMap?.applyIfNotNull { onStop() }
    }

    override fun onPause() {
        super.onPause()
        mMap?.applyIfNotNull { onPause() }
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mMap?.applyIfNotNull { onLowMemory() }
    }

    override fun onViewCreated() {
        mMap = binding.mapView
        launchAndRepeatWithViewLifecycle {
            foodDetailViewModel.foodDishData.collectLatest {
                when (it) {
                    is Result.Error -> {
                        Toast.makeText(
                            context,
                            "Ha habido un error al cargar los datos",
                            Toast.LENGTH_LONG
                        ).show()
                        Timber.e(it.exception)
                        findNavController().popBackStack()
                    }

                    Result.Loading -> {
                        //Colocar shimmer
                    }

                    is Result.Success -> setUiWithData(it.data)
                }
            }
        }
    }

    private fun setUiWithData(data: FoodDish) {
        binding.ivFoodImage.load(data.imageUrl)
        binding.tvStepsString.text = data.steps
        binding.tvFoodName.text = data.foodName
        binding.tvDescription.text = data.description
        val adapter = IngredientsDetailAdapter(data.filterTags)
        binding.rvIngredients.adapter = adapter


        val placeName = data.placeName
        val (latitude, longitude) = data.coordinates.split(",")
        val iconMap = requireContext().generateMarker(placeName)
        setMapView(latitude, longitude, iconMap)
        binding.tvNavigator.setOnClickListener {
            navigationToFragmentMap(latitude, longitude, placeName)
        }

    }

    private fun setMapView(latitude: String, longitude: String, iconMap: BitmapDescriptor) {
        val position = LatLng(latitude.toDouble(), longitude.toDouble())
        binding.mapView.getMapAsync {
            it.addMarker {
                position(position)
                icon(iconMap)
            }
            it.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    position,
                    resources.getDimension(R.dimen.zoom_dimension)
                )
            )
        }
    }

    private fun navigationToFragmentMap(lat: String, long: String, placeName: String) {
        ignoreException {
            findNavController().navigate(
                FoodDetailFragmentDirections.toMaps(
                    latValue = lat,
                    lonValue = long,
                    placeName = placeName
                )
            )
        }
    }

}