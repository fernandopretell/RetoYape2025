package com.fulbiopretell.retoyape2025.data

import com.fulbiopretell.retoyape2025.core.Failure
import com.fulbiopretell.retoyape2025.core.ResultType
import com.fulbiopretell.retoyape2025.core.toFailure
import com.fulbiopretell.retoyape2025.data.models.RecipeDto
import javax.inject.Inject

class RepositoryApp @Inject constructor(private val apiService: ApiService) : IRepositoryApp {

    override suspend fun getRecipes(): ResultType<List<RecipeDto>, Failure> {
        return try {
            val response = apiService.getRecipes()
            ResultType.Success(response)
        } catch (e: Exception) {
            ResultType.Error(e.toFailure())
        }
    }
}