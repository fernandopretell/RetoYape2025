package com.fulbiopretell.retoyape2025.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecipeDto(
    @SerialName("image")
    var image: String? = null,

    @SerialName("description")
    var description: String? = null,

    @SerialName("name")
    var name: String? = null,

    @SerialName("longitude")
    var longitude: Double? = null,

    @SerialName("latitude")
    var latitude: Double? = null
)