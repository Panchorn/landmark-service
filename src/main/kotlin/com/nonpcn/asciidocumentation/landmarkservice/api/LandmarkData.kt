package com.nonpcn.asciidocumentation.landmarkservice.api

data class LandmarkData(
    val id: String,
    val name: String,
    val country: String,
    val province: String,
    val type: String,
    val rating: Double
)