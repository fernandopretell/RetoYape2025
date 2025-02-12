package com.fulbiopretell.retoyape2025.ui.pages.home

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import com.fulbiopretell.retoyape2025.R
import com.fulbiopretell.retoyape2025.core.Resource
import com.fulbiopretell.retoyape2025.ui.models.Recipe

@Preview
@Composable
fun HomeScreenPreview() {
    val recipes = listOf(
        Recipe(
            image = "",
            name = "Ceviche",
            description = "",
            longitude = 0.0,
            latitude = 0.0
        ),
        Recipe(
            image = "",
            name = "Ocopa",
            description = "",
            longitude = 0.0,
            latitude = 0.0
        ),
        Recipe(
            image = "",
            name = "Alfajores",
            description = "",
            longitude = 0.0,
            latitude = 0.0
        ),
        Recipe(
            image = "",
            name = "Seco de Cordero",
            description = "",
            longitude = 0.0,
            latitude = 0.0
        ),
        Recipe(
            image = "",
            name = "Tacu Tacu",
            description = "",
            longitude = 0.0,
            latitude = 0.0
        )
    )

    HomeScreen(onRecipeClicked = {}, recipes = Resource.Success(recipes))
}

@Preview(showBackground = true)
@Composable
fun LoadingScreenPreview() {
    LoadingScreen()
}

@Preview(showBackground = true)
@Composable
fun ErrorScreenPreview() {
    ErrorScreen(errorMessage = "Something went wrong!")
}

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateToDetail: (Recipe) -> Unit
) {

    val context = LocalContext.current
    val recipes by viewModel.recipes.collectAsState()

    BackHandler {
        (context as? Activity)?.finish()
    }

    HomeScreen(
        onRecipeClicked = { recipe ->
            onNavigateToDetail(recipe)
        },
        recipes = recipes
    )
}

@Composable
fun HomeScreen(
    onRecipeClicked: (Recipe) -> Unit = {},
    recipes: Resource<List<Recipe>> = Resource.Idle,
) {
    var searchText by remember { mutableStateOf("") }

    when (recipes) {
        is Resource.Error -> {
            ErrorScreen(errorMessage = recipes.message)
        }

        Resource.Idle, Resource.Loading -> {
            LoadingScreen()
        }

        is Resource.Success -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
                    .windowInsetsPadding(WindowInsets.statusBars)
            ) {
                TextField(
                    value = searchText,
                    onValueChange = { searchText = it },
                    label = { Text("Buscar receta") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                        .testTag("searchTextField"),
                    textStyle = MaterialTheme.typography.bodyMedium
                )

                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 180.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    val filteredRecipes = recipes.data.filter {
                        it.name.contains(searchText, ignoreCase = true)
                    }

                    items(filteredRecipes) { recipe ->
                        RecipeItem(recipe, onClick = { onRecipeClicked(recipe) })
                    }
                }
            }
        }
    }
}

@Composable
fun RecipeItem(recipe: Recipe, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SubcomposeAsyncImage(
            model = recipe.image,
            contentDescription = "Imagen de ${recipe.name}",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(Color.LightGray),
            contentScale = ContentScale.Crop,
            loading = {
                Image(
                    painter = painterResource(R.drawable.fail_recipe),
                    contentDescription
                )
            },
            error = {
                Image(
                    painter = painterResource(R.drawable.fail_recipe),
                    contentDescription
                )
            }
        )
        Text(
            text = recipe.name,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
            modifier = Modifier
                .padding(top = 4.dp)
                .align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier
            .testTag("loadingIndicator")
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorScreen(errorMessage: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
            .padding(16.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Error: $errorMessage", color = MaterialTheme.colorScheme.error)
        }
    }
}







