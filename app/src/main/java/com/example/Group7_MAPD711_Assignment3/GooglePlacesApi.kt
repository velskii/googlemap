/**
 * File: GooglePlacesApi.kt
 * Group 7
 * 1. QUOC PHONG NGO - 301148406
 * 2. FEILIANG ZHOU  - 301216989
 */
package com.example.Group7_MAPD711_Assignment3

import Group7_MAPD711_Assignment3.R
import android.content.Context
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

class GooglePlacesApi {
    public fun getPlacesJson(context: Context,
                             lat: Double,
                             lng: Double,
                             radius: Int,
                             type: String) : String {
        var result = ""
        try {
            var key: String = context.resources.getString(R.string.places_api_key)
            var urlStr: String = "https://maps.googleapis.com/maps/api/place/search/json?location=$lat,$lng&radius=$radius&type=$type&key=$key"
            var url: URL = URL(urlStr)
            val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
            connection.setRequestProperty("Content-Type", "application/json")
            connection.requestMethod = "GET"
            connection.doInput = true
            val br = connection.inputStream.bufferedReader()
            result = br.use { br.readText() }
            connection.disconnect()

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return result
    }
}