package com.android.movieschallenge.presentation.movies

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import coil.compose.AsyncImage
import com.android.movieschallenge.di.Constants
import com.android.movieschallenge.domain.model.Movie
import com.android.movieschallenge.presentation.LoaderFullScreen

@Composable
fun DetailMovieScreen(lifeCycleOwner: LifecycleOwner, viewModel: MoviesViewModel = hiltViewModel(), id: Int) {
    val movie = remember { mutableStateOf<Movie?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    LaunchedEffect(id) {
        viewModel.getMovie(id)
    }

    LaunchedEffect(movie.value) {
        if (movie.value != null) {
            isLoading = false
        }
    }
    viewModel.movie.observe(lifeCycleOwner) { it ->
        movie.value = it
    }
    Column {
        if (isLoading) {
            LoaderFullScreen()
        } else {
            movie.value?.let { movie ->
                Text(text = "id :${movie.id}", color = Color.Black)
                Text(text = "title: ${movie.title}", color = Color.Black)
                AsyncImage(
                    model = Constants.BASE_IMAGE_URL + movie.posterPath,
                    contentDescription = movie.title,
                )
            }
        }
    }

}