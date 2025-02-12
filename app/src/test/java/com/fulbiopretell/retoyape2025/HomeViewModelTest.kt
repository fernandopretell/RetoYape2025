package com.fulbiopretell.retoyape2025

import app.cash.turbine.test
import com.fulbiopretell.retoyape2025.core.Failure
import com.fulbiopretell.retoyape2025.core.Resource
import com.fulbiopretell.retoyape2025.core.ResultType
import com.fulbiopretell.retoyape2025.data.IRepositoryApp
import com.fulbiopretell.retoyape2025.data.models.RecipeDto
import com.fulbiopretell.retoyape2025.mappers.toListModelRecipe
import com.fulbiopretell.retoyape2025.ui.pages.home.HomeViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var homeViewModel: HomeViewModel
    private val repositoryApp: IRepositoryApp = mockk(relaxed = true)

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        homeViewModel = HomeViewModel(repositoryApp)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchRecipes should emit Loading and then Success when data is fetched successfully`() = runTest {

        val mockRecipes = listOf(
            RecipeDto(image = "", name = "Ceviche", description = "", longitude = 0.0, latitude = 0.0)
        )
        val expectedRecipes = mockRecipes.toListModelRecipe()
        coEvery { repositoryApp.getRecipes() } returns ResultType.Success(mockRecipes)

        homeViewModel.recipes.test {
            homeViewModel.fetchRecipes()

            assertEquals(Resource.Loading, awaitItem())
            val successState = awaitItem()
            assertTrue(successState is Resource.Success)
            assertEquals(expectedRecipes, (successState as Resource.Success).data)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `fetchRecipes should emit Loading and then Error when API fails`() = runTest {

        coEvery { repositoryApp.getRecipes() } returns ResultType.Error(Failure.IOFailure(message = "Error"))

        homeViewModel.recipes.test {
            homeViewModel.fetchRecipes()

            assertEquals(Resource.Loading, awaitItem())
            val errorState = awaitItem()
            assertTrue(errorState is Resource.Error)
            assertEquals("Error", (errorState as Resource.Error).message)

            cancelAndIgnoreRemainingEvents()
        }
    }
}






