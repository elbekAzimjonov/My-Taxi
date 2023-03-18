package com.elbek.mytaxi.presentation.fragment

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.elbek.mytaxi.R
import com.elbek.mytaxi.core.util.LocationProvider
import com.elbek.mytaxi.data.model.LocationModel
import com.elbek.mytaxi.databinding.FragmentMapBinding
import com.elbek.mytaxi.presentation.viewmodel.LocationViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MapFragment() : Fragment(), OnMapReadyCallback {
    private val viewModel by viewModels<LocationViewModel>()
    private var locationJob: Job? = null
    private lateinit var mMap: GoogleMap
    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!
    private var userLocationMarker: Marker? = null
    private var startZoom: Boolean = true
    private lateinit var liveLocation: MutableLiveData<Location>

    @Inject
    lateinit var location: LocationProvider

    companion object {
        const val INTERVAL = 10_000L
    }

    override fun onStart() {
        super.onStart()
        onBind()
        observeLocation()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        binding.plus.setOnClickListener {
            mMap.animateCamera(CameraUpdateFactory.zoomIn())
        }
        binding.minus.setOnClickListener {
            mMap.animateCamera(CameraUpdateFactory.zoomOut())
        }
        binding.mylocation.setOnClickListener {
            if (liveLocation.value != null) {
                setUserLocationMarker(liveLocation.value!!, 16f)
            } else {
                Toast.makeText(requireContext(), "Location not yet received", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        return binding.root
    }

    private fun onBind() {
        liveLocation = MutableLiveData()
    }

    override fun onMapReady(p0: GoogleMap) {
        mMap = p0
        mMap.uiSettings.apply {
            isCompassEnabled = false
        }
    }

    private fun observeLocation() {
        locationJob?.cancel()
        locationJob = lifecycleScope.launch {
            location.getLocation(INTERVAL)
                .catch { it.printStackTrace() }
                .collectLatest { location ->
                    if (liveLocation.value != null) {
                        setUserLocationMarker(liveLocation.value!!, 16f)
                    }
                    liveLocation.value = location
                    viewModel.onUpdateLocation(
                        LocationModel(
                            longitude = location.longitude,
                            latitude = location.latitude
                        )
                    )
                }
        }
    }

    private fun setUserLocationMarker(location: Location, zoom: Float) {
        val latLng = LatLng(location.latitude, location.longitude)
        if (userLocationMarker == null) {
            try {
                val markerOptions = MarkerOptions()
                markerOptions.position(latLng)
                markerOptions.icon(
                    bitmapFromVector(requireActivity(), R.drawable.ic_location_marker)
                )
                markerOptions.anchor(0.5f, 0.5f)
                userLocationMarker = mMap.addMarker(markerOptions)
                userLocationMarker!!.isFlat = false
            } catch (_: IllegalStateException) {

            }
        } else {
            userLocationMarker!!.position = latLng
            userLocationMarker!!.setAnchor(0.5f, 0.5f)
        }
        val comPos = CameraPosition.fromLatLngZoom(latLng, zoom)
        val com = CameraPosition.builder(comPos).bearing(location.bearing).build()
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(com))
        startZoom = false
    }

    private fun bitmapFromVector(context: Context, vectorResId: Int): BitmapDescriptor {
        val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)
        vectorDrawable!!.setBounds(
            0,
            0,
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight
        )

        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )

        val canvas = Canvas(bitmap)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mMap.clear()
        _binding = null
    }
}