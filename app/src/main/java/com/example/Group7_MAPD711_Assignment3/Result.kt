package com.example.Group7_MAPD711_Assignment3

data class Result (
    val geometry: QuerySearchGeometryInfo,
    val icon: String,
    val id: String,
    val name: String,
    val opening_hours: QuerySearchOpeningInfo,
    val photos: List<QuerySearchPhotosInfo>,
    val place_id: String,
    val scope: String,
    val alt_ids: List<QuerySearchAlteranteIdInfo>,
    val reference: String,
    val types: List<String>,
    val vicinity: String
)
