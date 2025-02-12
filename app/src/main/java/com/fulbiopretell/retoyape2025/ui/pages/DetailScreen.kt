package com.fulbiopretell.retoyape2025.ui.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.fulbiopretell.retoyape2025.R
import com.fulbiopretell.retoyape2025.ui.models.Recipe

@Preview
@Composable
fun DetailScreenPreview() {

    val recipe = Recipe(
        image = "",
        name = "Ceviche",
        description = "El ceviche es una de las piedras angulares de la gastronomía peruana. Este plato de pescado crudo se marina en jugo de limón y se mezcla con cebolla roja, ají y cilantro. Se sirve generalmente con camote y maíz tostado.",
        longitude = 0.0,
        latitude = 0.0
    )

    DetailScreen(recipe = recipe, onNavigateToMap = { _, _ -> })
}

@Composable
fun DetailScreen(
    recipe: Recipe,
    onNavigateToMap: (Double, Double) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .windowInsetsPadding(WindowInsets.statusBars)
    ) {

        SubcomposeAsyncImage(
            model = recipe.image,
            contentDescription = "Imagen de ${recipe.name}",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentScale = ContentScale.Crop,
            loading = {
                Image(
                    painter = painterResource(R.drawable.fail_recipe),
                    contentDescription
                )
            },
            error = {
                Image(
                    modifier = Modifier.testTag("errorImage") ,
                    painter = painterResource(R.drawable.fail_recipe),
                    contentDescription = "Imagen fallida"
                )
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = recipe.name,
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold
            )
        )

        Text(
            text = recipe.description,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Medium
            ),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Justify
        )

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = { onNavigateToMap(recipe.latitude, recipe.longitude) }) {
                Text("Ver en Mapa")
            }
        }
    }
}
