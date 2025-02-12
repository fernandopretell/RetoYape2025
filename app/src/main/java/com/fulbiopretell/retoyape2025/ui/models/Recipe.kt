package com.fulbiopretell.retoyape2025.ui.models

import kotlinx.serialization.Serializable

@Serializable
data class Recipe(
    var image: String,
    var description: String,
    var name: String,
    var longitude: Double,
    var latitude: Double
)