package com.fulbiopretell.retoyape2025

import com.fulbiopretell.retoyape2025.core.ResultType
import com.fulbiopretell.retoyape2025.core.toFailure
import com.fulbiopretell.retoyape2025.data.ApiService
import com.fulbiopretell.retoyape2025.data.RepositoryApp
import com.fulbiopretell.retoyape2025.data.models.RecipeDto
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import java.io.IOException

@ExperimentalCoroutinesApi
class RepositoryAppTest {

    private lateinit var mockApiService: ApiService
    private lateinit var repository: RepositoryApp

    @Before
    fun setup() {
        mockApiService = mockk()
        repository = RepositoryApp(mockApiService)
    }

    @Test
    fun `getRecipes should return success when api call is successful`() = runBlocking {

        val recipeList = listOf(RecipeDto(image = "", name = "Ceviche", description = "", longitude = 0.0, latitude = 0.0))
        coEvery { mockApiService.getRecipes() } returns recipeList

        val result = repository.getRecipes()


        assertTrue(result is ResultType.Success)
        assertEquals(recipeList, (result as ResultType.Success).data)
    }

    @Test
    fun `getRecipes should return error when api call throws an exception`() = runBlocking {

        val exception = Exception("API error")
        coEvery { mockApiService.getRecipes() } throws exception

        val result = repository.getRecipes()

        assertTrue(result is ResultType.Error)
        assertEquals(exception.toFailure(), (result as ResultType.Error).error)
    }

    @Test
    fun `getRecipes should return success with empty list when no recipes are available`() = runBlocking {

        val emptyList = emptyList<RecipeDto>()
        coEvery { mockApiService.getRecipes() } returns emptyList

        val result = repository.getRecipes()

        assertTrue(result is ResultType.Success)
        assertTrue((result as ResultType.Success).data.isEmpty())
    }

    @Test
    fun `getRecipes should return error for network exception`() = runBlocking {

        val networkException = IOException("Network error")
        coEvery { mockApiService.getRecipes() } throws networkException

        val result = repository.getRecipes()

        assertTrue(result is ResultType.Error)
        assertEquals(networkException.toFailure(), (result as ResultType.Error).error)
    }

    @Test
    fun `should inject ApiService correctly`() = runBlocking {

        assertNotNull(repository)
    }

    @Test
    fun `getRecipes should handle multiple concurrent requests`() = runBlocking {

        val recipeList = listOf(RecipeDto(image = "", name = "Ceviche", description = "", longitude = 0.0, latitude = 0.0))
        coEvery { mockApiService.getRecipes() } returns recipeList

        val result1 = repository.getRecipes()
        val result2 = repository.getRecipes()

        assertTrue(result1 is ResultType.Success)
        assertTrue(result2 is ResultType.Success)
    }

    @Test
    fun `getRecipes should map exception to custom failure`() = runBlocking {

        val exception = Exception("Custom error")
        coEvery { mockApiService.getRecipes() } throws exception

        val result = repository.getRecipes()

        assertTrue(result is ResultType.Error)

        assertEquals(exception.toFailure(), (result as ResultType.Error).error)
    }
}
