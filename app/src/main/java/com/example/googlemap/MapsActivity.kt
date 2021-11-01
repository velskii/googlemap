package com.example.googlemap

import android.content.Context
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.loader.content.AsyncTaskLoader

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.googlemap.databinding.ActivityMapsBinding
import com.google.android.gms.maps.model.Marker
import com.google.gson.GsonBuilder

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
//    private var markerPerth: Marker? = null
//    private var markerSydney: Marker? = null
//    private var markerBrisbane: Marker? = null
//
//    private val PERTH = LatLng(-31.952854, 115.857342)
//    private val SYDNEY = LatLng(-33.87365, 151.20689)
//    private val BRISBANE = LatLng(-27.47093, 153.0235)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(map: GoogleMap) {
//        mMap = googleMap
//
//        // Add a marker in Sydney and move the camera
//        val sydney = LatLng(43.6532, -79.3832)
//        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        // Add some markers to the map, and add a data object to each marker.
        mMap = map
        HitApi(this@MapsActivity, 43.6532, -79.3832, 5000, "travel_agency").execute()
//        markerPerth = map.addMarker(
//            MarkerOptions()
//                .position(PERTH)
//                .title("Perth")
//        )
//        markerPerth?.tag = 0
//        markerSydney = map.addMarker(
//            MarkerOptions()
//                .position(SYDNEY)
//                .title("Sydney")
//        )
//        markerSydney?.tag = 0
//        markerBrisbane = map.addMarker(
//            MarkerOptions()
//                .position(BRISBANE)
//                .title("Brisbane")
//        )
//        markerBrisbane?.tag = 0
//
//        // Set a listener for marker click.
//        map.setOnMarkerClickListener(this)
    }

    private inner class HitApi : AsyncTask<Void, Void, String> {
        var context: Context? = null
        var lat: Double? = null
        var lng: Double? = null
        var radius: Int? = null
        var type: String? = null

        constructor(
            context: Context,
            lat: Double,
            lng: Double,
            radius: Int,
            type: String
        ) {
            this.context = context
            this.lat = lat
            this.lng = lng
            this.radius = radius
            this.type = type
        }

        override fun doInBackground(vararg p0: Void?): String {
            return  GooglePlacesApi().getPlacesJson(context as Context,
                lat as Double,
                lng as Double,
                radius as Int,
                type as String)
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            val gson = GsonBuilder().create()
            val root = gson.fromJson(result, PlacesRoot::class.java)
            addMarkers(root)
        }
    }

    public fun addMarkers(root: PlacesRoot) {
        for(result : Result in root.results) {
            val p = LatLng(result.geometry?.location?.lat!!, result.geometry?.location?.lng!!)
            mMap.addMarker(MarkerOptions().position(p).title(result.name))
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(43.6532,-79.3832)))
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15f))
    }


    override fun onMarkerClick(p0: Marker?): Boolean {
        // Retrieve the data from the marker.
        val clickCount = p0?.tag as? Int

        // Check if a click count was set, then display the click count.
        clickCount?.let {
            val newClickCount = it + 1
            p0.tag = newClickCount
            Toast.makeText(
                this,
                "${p0.title} has been clicked $newClickCount times.",
                Toast.LENGTH_SHORT
            ).show()
        }

        return false
    }
}
