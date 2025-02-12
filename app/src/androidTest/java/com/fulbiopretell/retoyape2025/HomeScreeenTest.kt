package com.fulbiopretell.retoyape2025

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.fulbiopretell.retoyape2025.core.Resource
import com.fulbiopretell.retoyape2025.ui.models.Recipe
import com.fulbiopretell.retoyape2025.ui.pages.home.HomeScreen
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testHomeScreen_LoadingState() {
        val loadingState = Resource.Loading

        composeTestRule.setContent {
            HomeScreen(
                recipes = loadingState,
                onRecipeClicked = {}
            )
        }

        composeTestRule.onNodeWithTag("loadingIndicator").assertIsDisplayed()
    }

    @Test
    fun testHomeScreen_ErrorState() {
        val errorMessage = "Error cargando recetas"

        composeTestRule.setContent {
            HomeScreen(
                onRecipeClicked = {},
                recipes = Resource.Error(errorMessage)
            )
        }

        composeTestRule.onNodeWithText("Error: $errorMessage").assertIsDisplayed()
    }

    @Test
    fun testHomeScreen_SuccessState() {
        val recipes = listOf(
            Recipe("1", "", "Ceviche", 0.0, 0.0),
            Recipe("2", "", "Ocopa", 0.0, 0.0)
        )

        composeTestRule.setContent {
            HomeScreen(
                onRecipeClicked = {},
                recipes = Resource.Success(recipes)
            )
        }

        composeTestRule.onNodeWithText("Ceviche").assertIsDisplayed()
        composeTestRule.onNodeWithText("Ocopa").assertIsDisplayed()
    }

    @Test
    fun testHomeScreen_onRecipeClicked() {
        val recipes = listOf(
            Recipe("1", "Ceviche es el mejor plato del mundo.", "Ceviche", 0.0, 0.0)
        )

        var clickedRecipe: Recipe? = null

        composeTestRule.setContent {
            HomeScreen(
                onRecipeClicked = {
                    clickedRecipe = it
                },
                recipes = Resource.Success(recipes)
            )
        }

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText("Ceviche", ignoreCase = true)
            .assertExists()
            .assertIsDisplayed()
            .performClick()

        assert(clickedRecipe?.name == "Ceviche")
    }

    @Test
    fun testHomeScreen_SearchFunctionality() {
        val recipes = listOf(
            Recipe("1", "", "Ceviche", 0.0, 0.0),
            Recipe("2", "", "Escabeche", 0.0, 0.0)
        )

        composeTestRule.setContent {
            HomeScreen(
                onRecipeClicked = {},
                recipes = Resource.Success(recipes)
            )
        }

        composeTestRule.onNodeWithTag("searchTextField")
            .performTextInput("che")

        composeTestRule.onNodeWithText("Ceviche").assertIsDisplayed()
        composeTestRule.onNodeWithText("Escabeche").assertIsDisplayed()
    }
}
