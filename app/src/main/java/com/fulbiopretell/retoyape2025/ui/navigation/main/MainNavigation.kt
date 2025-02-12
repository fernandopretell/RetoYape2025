package com.fulbiopretell.retoyape2025.ui.navigation.main

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fulbiopretell.retoyape2025.ui.models.Recipe
import com.fulbiopretell.retoyape2025.ui.pages.DetailScreen
import com.fulbiopretell.retoyape2025.ui.pages.home.HomeScreen
import com.fulbiopretell.retoyape2025.ui.pages.MapScreen
import kotlinx.serialization.json.Json

@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = MainRoute.Home.route
    ) {
        composable(route = MainRoute.Home.route) { backStackEntry ->
            HomeScreen(
                onNavigateToDetail = { recipe ->
                    navController.navigate(MainRoute.Detail.createRoute(recipe))
                }
            )
        }
        composable(route = MainRoute.Detail.route) { backStackEntry ->
            val recipeJson = backStackEntry.arguments?.getString("recipe")
            val recipe: Recipe? = recipeJson?.let {
                Json.decodeFromString<Recipe>(it)
            }

            recipe?.let {
                DetailScreen(recipe = it, onNavigateToMap = { latitude, longitude ->
                    navController.navigate(MainRoute.Map.createRoute(it))
                })
            }
        }
        composable(route = MainRoute.Map.route) { backStackEntry ->
            val recipeJson = backStackEntry.arguments?.getString("recipe")
            val recipe: Recipe? = recipeJson?.let {
                Json.decodeFromString<Recipe>(it)
            }
            recipe?.let {
                MapScreen(recipe = it, onNavigateToHome = {
                    navController.navigate(MainRoute.Home.route) {
                        popUpTo(backStackEntry.destination.id) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                })
            }

        }
    }
}