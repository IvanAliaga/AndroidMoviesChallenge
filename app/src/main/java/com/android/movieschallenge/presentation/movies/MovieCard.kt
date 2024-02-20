package com.android.movieschallenge.presentation.movies

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.android.movieschallenge.di.Constants
import com.android.movieschallenge.domain.model.Movie

@Stable
@Composable
fun MovieCard(
    movie: Movie,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Imagen en el lado izquierdo

            AsyncImage(
                model = Constants.BASE_IMAGE_URL + movie.posterPath,
                contentDescription = movie.title,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop,
                placeholder = ColorPainter(color = Color.Gray),
                error = ColorPainter(color = Color.Gray),
                onError = {
                    Log.e("AsyncImage", "Error: " + it.result.toString())
                }
            )

            // Espacio entre la imagen y el título/descripción
            Spacer(modifier = Modifier.width(16.dp))

            // Título y descripción en el lado derecho
            Column {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.h6,
                    fontFamily = FontFamily.Serif,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Premiere: " + movie.releaseDate,
                    style = MaterialTheme.typography.body2,
                    fontFamily = FontFamily.Serif,
                    color = Color.Gray,
                    modifier = Modifier.widthIn(max = 200.dp) // Limitar la anchura de la descripción
                )
            }
        }
    }
}
