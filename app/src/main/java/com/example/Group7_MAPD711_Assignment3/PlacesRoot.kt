package com.example.Group7_MAPD711_Assignment3

data class PlacesRoot (
    val error_message: String?,
    val html_attributions: List<String>,
    val results: List<Result>,
    val status: String
)