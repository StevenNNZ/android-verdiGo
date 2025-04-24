package com.example.verdigo.presentation.fragments

import LocationAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.verdigo.R
import com.example.verdigo.data.model.Location
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private lateinit var mapView: MapView
    private lateinit var currentLocation: TextView
    private  var locations: List<Location> = listOf(
    Location("Calle 85 #12-34, Chapinero", LatLng(4.670715, -74.056463)),
    Location("Carrera 50 #26-10, Teusaquillo", LatLng(4.639041, -74.090741)),
    Location("Avenida Caracas #45-67, Chapinero", LatLng(4.648441, -74.062831)),
    Location("Calle 170 #15-89, Usaquén", LatLng(4.764427, -74.040654)),
    Location("Carrera 7 #72-65, Chapinero", LatLng(4.653332, -74.052196))
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_maps, container, false)
        mapView = view.findViewById(R.id.map)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
        currentLocation = view.findViewById(R.id.specific_location)



        locations
        currentLocation.text = locations[0].address

        val listView: ListView = view.findViewById(R.id.locations_list)
        val adapter = LocationAdapter(requireContext(), locations) { location ->
            showLocationOnMap(location)
        }
        listView.adapter = adapter

        return view
    }

    private fun showLocationOnMap(location: Location) {
        val markerOptions = MarkerOptions().position(location.latLng).title(location.address)
        map.clear()
        map.addMarker(markerOptions)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(location.latLng, 15f))

        currentLocation.text =(location.address)

    }

    override fun onMapReady(googleMap: GoogleMap) {
        MapsInitializer.initialize(requireContext())
        map = googleMap

        val bogota = locations[0].latLng
        map.addMarker(MarkerOptions().position(bogota).title("Ubicación actual"))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(bogota, 12f))

        addStoreLocations()
    }

    private fun addStoreLocations() {
        val locations = listOf(
            LatLng(4.653332, -74.052196),
            LatLng(4.681487, -74.044914),
            LatLng(4.648625, -74.074549)
        )



        for (location in locations) {
            map.addMarker(MarkerOptions().position(location).title("Tienda"))
        }
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }
}