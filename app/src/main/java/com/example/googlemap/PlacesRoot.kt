package com.example.googlemap

import android.content.Context

data class PlacesRoot (
    val error_message: String?,
    val html_attributions: List<String>,
    val results: List<Result>,
    val status: String
)