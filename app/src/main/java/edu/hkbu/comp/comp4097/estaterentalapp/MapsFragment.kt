package edu.hkbu.comp.comp4097.estaterentalapp

import android.location.Address
import android.location.Geocoder
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import edu.hkbu.comp.comp4097.estaterentalapp.data.AppDatabase
import edu.hkbu.comp.comp4097.estaterentalapp.data.Location
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

class MapsFragment : Fragment() {

    private var columnCount = 1

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        lateinit var location: String
        location = arguments?.getString("housesLocation").toString()

        CoroutineScope(Dispatchers.IO).launch {
            val dao = AppDatabase.getInstance(requireContext()).locationDao()
            val locationByDB : Location?
            locationByDB = dao.findCoordinatesByEstate(location)
            Log.d("DB", "Location found: ${locationByDB}")

            if(locationByDB == null){
            Log.d("DB", "Insert new location to locationByDB")
            var addressList: List<Address>? = null
            val geoCoder = Geocoder(activity)
            try {
                addressList = geoCoder.getFromLocationName(location, 1)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            val address = addressList!![0]
            Log.d("Map", "address: ${address}")
            val latLng = LatLng(address.latitude, address.longitude)
            Log.d("Map", "latLng: ${latLng}")
            var newLocation = Location(location, address.latitude.toString(), address.longitude.toString())
            dao.insert(newLocation)
            CoroutineScope(Dispatchers.Main).launch {
                Toast.makeText(activity, "${location} is searching",
                    Toast.LENGTH_SHORT).show()
                googleMap!!.addMarker(MarkerOptions().position(latLng).title(location))
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18F))
            }
            }else{
                Log.d("DB", "Read location from locationDao")
                val latLng = LatLng(locationByDB.latitude.toDouble(), locationByDB.longitude.toDouble())
                CoroutineScope(Dispatchers.Main).launch {
                    Toast.makeText(activity, "${location} is searching",
                        Toast.LENGTH_SHORT).show()
                    googleMap!!.addMarker(MarkerOptions().position(latLng).title(location))
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18F))
                }
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments?.let {
            columnCount = it.getInt(detailFragment.ARG_COLUMN_COUNT)
        }

        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            MapsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}