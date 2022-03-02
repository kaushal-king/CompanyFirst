package com.the.firsttask.ui.map

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.internal.zzdv
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.the.firsttask.R
import com.the.firsttask.databinding.FragmentMapBinding


class MapFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private lateinit var _binding: FragmentMapBinding
    private val binding get() = _binding
    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    lateinit var searchPlaceLauncher: ActivityResultLauncher<Intent>
    lateinit var locationPermissionLauncher: ActivityResultLauncher<Array<String>>
    private val permission = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,

        )

    override fun onCreate(savedInstanceState: Bundle?) {
        searchPlaceLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            Log.e("TAG", result.resultCode.toString())


            val status = Autocomplete.getStatusFromIntent(result.data)
            Log.i("TAG", status.statusMessage.toString()+"place message")

            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.let {
                    val place = Autocomplete.getPlaceFromIntent(result.data)
                    Log.i("TAG", "Place: ${place.name}, ${place.id}")
                }
            }
            if (result.resultCode == AutocompleteActivity.RESULT_ERROR) {
                result.data?.let {
                    val status = Autocomplete.getStatusFromIntent(result.data)
                    Log.i("TAG", status.statusMessage.toString())
                }
            }


        }


        locationPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
                if (!permissions.containsValue(false)) {
                    if (ActivityCompat.checkSelfPermission(
                            requireContext(),
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        mMap.isMyLocationEnabled = true
                    }
                }
            }
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_map, container, false
        )
        val root: View = _binding.root
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        if (!Places.isInitialized()) {
            Places.initialize(context, "AIzaSyCgD7l72L8V7aOLRmFL61yTcOVV1LRB7t8")
            Log.e("TAG", "Places Initialized", )
        }

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map_view) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)

        binding.ivSearchPlace.setOnClickListener {
            searchPlace()
        }

        return root
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                updateMapLocation(location)
            }


        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.isMyLocationEnabled = true
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestlocationPermission()
            }
        }
        mMap.setOnMarkerClickListener(this)
    }


    private fun requestlocationPermission() {

        when {
            hasPermissions(requireContext(), *permission) -> {
                if (ActivityCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    mMap.isMyLocationEnabled = true
                }
            }
            else -> {
                Toast.makeText(requireContext(), " Allow the  Permission", Toast.LENGTH_LONG).show()
                locationPermission()
            }
        }

    }

    private fun hasPermissions(context: Context, vararg permissions: String): Boolean =
        permissions.all {
            ActivityCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }


    private fun locationPermission() {


        locationPermissionLauncher.launch(permission)
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        return false
    }

    private fun updateMapLocation(location: Location?) {
        mMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(
                    location?.latitude ?: 0.0,
                    location?.longitude ?: 0.0
                ), 18.0f
            )
        )

    }

    fun searchPlace() {
        val fields =
            listOf(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG)
        val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
            .build(requireActivity())
        searchPlaceLauncher.launch(intent)


    }


//     override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        if (requestCode == ConstantHelper. AUTOCOMPLETE_REQUEST_CODE) {
//            if (resultCode == RESULT_OK) {
//                val place = Autocomplete.getPlaceFromIntent(data)
//                Log.i("TAG", "Place: " + place.name + ", " + place.id)
//            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
//            } else if (resultCode == RESULT_CANCELED) {
//
//            }
//        }
//    }
}