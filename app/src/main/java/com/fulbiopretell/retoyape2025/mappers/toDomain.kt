package com.fulbiopretell.retoyape2025.mappers

import com.fulbiopretell.retoyape2025.data.models.RecipeDto
import com.fulbiopretell.retoyape2025.ui.models.Recipe

fun RecipeDto.toModel() = Recipe(
    description = this.description ?: "",
    image = this.image ?: "",
    name = this.name ?: "",
    latitude = this.latitude ?: 0.0,
    longitude = this.longitude ?: 0.0

)

fun List<RecipeDto>.toListModelRecipe(): List<Recipe> {
    return this.map { it.toModel() }
}