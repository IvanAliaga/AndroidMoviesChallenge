package com.android.movieschallenge.presentation

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.android.movieschallenge.domain.model.Movie

@Composable
fun MoviesScreen(lifeCycleOwner: LifecycleOwner, viewModel: MoviesViewModel = hiltViewModel()) {
    var listMovies by remember { mutableStateOf<List<Movie>>(emptyList()) }
    var visibilityMessage by remember {
        mutableStateOf(true)
    }
    viewModel.getMovies()
    viewModel.movies.observe(lifeCycleOwner, Observer {
        Log.e("Movies", "movies: $it")
        listMovies = listMovies.plus(it)
    })
    viewModel.isLoading.observe(lifeCycleOwner, Observer {
        visibilityMessage = it
    })
    AnimatedVisibility(visible = visibilityMessage) {
        Text(text = "Cargando...", color = Color.Black)
    }
    AnimatedVisibility(visible = !visibilityMessage) {
        MoviesList(listMovies, viewModel)
    }

}

@Composable
fun MoviesList(liveMovies: List<Movie>, viewModel: MoviesViewModel) {

    LazyColumn {
        items(liveMovies.size) {
            // Muestra el elemento en la posición actual de la lista
            Text(text = "Position: ${it}")
            Text(text = "Item ${liveMovies[it]}")

            // Si estamos cerca del final de la lista, agregamos más elementos
            if (it == liveMovies.lastIndex) {
                viewModel.getMoreMovies(2)
            }
        }
    }
}