package com.fulbiopretell.retoyape2025.data

import com.fulbiopretell.retoyape2025.core.Failure
import com.fulbiopretell.retoyape2025.core.ResultType
import com.fulbiopretell.retoyape2025.data.models.RecipeDto

interface IRepositoryApp {
    suspend fun getRecipes(): ResultType<List<RecipeDto>, Failure>
}