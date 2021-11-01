/**
 * File: MapsActivity.kt
 * Group 7
 * 1. QUOC PHONG NGO - 301148406
 * 2. FEILIANG ZHOU  - 301216989
 */
package com.example.googlemap

import android.content.Context
import android.location.Geocoder
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast

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
        // Add some markers to the map, and add a data object to each marker.
        mMap = map
        mMap.uiSettings.isZoomControlsEnabled = true

        HitApi(this@MapsActivity, 43.6532, -79.3832, 5000, "travel_agency").execute()

        // Set a listener for marker click.
        map.setOnMarkerClickListener(this)
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

    /**
     * Draw marker
     */
    public fun addMarkers(root: PlacesRoot) {
        for(result : Result in root.results) {
            val p = LatLng(result.geometry?.location?.lat!!, result.geometry?.location?.lng!!)
            mMap.addMarker(MarkerOptions().position(p).title(result.name).snippet(getAddress(p)))
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(43.6532,-79.3832)))
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15f))
    }

    /**
     * Get address of selected marker
     */
    private fun getAddress(lat: LatLng): String? {
        val geocoder = Geocoder(this)
        val list = geocoder.getFromLocation(lat.latitude, lat.longitude,1)
        return list[0].getAddressLine(0)
    }

    override fun onMarkerClick(p0: Marker?): Boolean {
        // Check if a click count was set, then display the click count.
        Toast.makeText(
                this,
                "${p0?.position?.latitude}",
                Toast.LENGTH_LONG
        ).show()

        return false
    }
}
