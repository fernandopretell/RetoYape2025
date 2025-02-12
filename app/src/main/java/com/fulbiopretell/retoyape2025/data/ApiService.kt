package com.fulbiopretell.retoyape2025.data

import com.fulbiopretell.retoyape2025.data.models.RecipeDto
import retrofit2.http.GET

interface ApiService {

    @GET("getRecipes/")
    suspend fun getRecipes(): List<RecipeDto>
}