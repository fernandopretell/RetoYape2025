package com.fulbiopretell.retoyape2025.ui.navigation.main

import com.fulbiopretell.retoyape2025.ui.models.Recipe
import com.fulbiopretell.retoyape2025.ui.navigation.NavArgs
import com.fulbiopretell.retoyape2025.ui.util.toUriEncoded

sealed class MainRoute(val route: String) {
    data object Home : MainRoute("Home")

    data object Detail :
        MainRoute("Detail/{${NavArgs.Recipe.key}}") {
        fun createRoute(recipe: Recipe): String =
            "Detail/${recipe.toUriEncoded()}"
    }

    data object Map : MainRoute("Map/{${NavArgs.Recipe.key}}") {
        fun createRoute(recipe: Recipe): String =
            "Map/${recipe.toUriEncoded()}"
    }

}