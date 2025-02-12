package com.fulbiopretell.retoyape2025

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.fulbiopretell.retoyape2025.ui.models.Recipe
import com.fulbiopretell.retoyape2025.ui.pages.DetailScreen
import org.junit.Rule
import org.junit.Test

class DetailScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testDetailScreen_DisplayElements() {
        val recipe = Recipe(
            image = "",
            name = "Ceviche",
            description = "El ceviche es el mejor plato del mundo.",
            longitude = 0.0,
            latitude = 0.0
        )

        composeTestRule.setContent {
            DetailScreen(recipe = recipe, onNavigateToMap = { _, _ -> })
        }

        composeTestRule.onNodeWithText("Ceviche").assertIsDisplayed()

        composeTestRule.onNodeWithText("El ceviche es el mejor plato del mundo.").assertIsDisplayed()

        composeTestRule.onNodeWithText("Ver en Mapa").assertIsDisplayed()

        composeTestRule.onNodeWithContentDescription("Imagen fallida").assertIsDisplayed()
    }

    @Test
    fun testDetailScreen_NavigateToMap() {
        val recipe = Recipe(
            image = "",
            name = "Ceviche",
            description = "El ceviche es el mejor plato del mundo.",
            longitude = 12.34,
            latitude = 56.78
        )

        var mapCoordinates: Pair<Double, Double>? = null

        composeTestRule.setContent {
            DetailScreen(recipe = recipe, onNavigateToMap = { latitude, longitude ->
                mapCoordinates = Pair(latitude, longitude)
            })
        }

        composeTestRule.onNodeWithText("Ver en Mapa").performClick()

        assert(mapCoordinates == Pair(56.78, 12.34))
    }

    @Test
    fun testDetailScreen_ErrorImage() {
        val recipe = Recipe(
            image = "invalid_image_url",
            name = "Ceviche",
            description = "El ceviche es el mejor plato del mundo.",
            longitude = 0.0,
            latitude = 0.0
        )

        composeTestRule.setContent {
            DetailScreen(recipe = recipe, onNavigateToMap = { _, _ -> })
        }

        composeTestRule.onNodeWithTag("errorImage").assertIsDisplayed()
    }

}