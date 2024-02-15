package com.android.movieschallenge.presentation.movies

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
    val listMovies = remember { mutableStateListOf<Movie>() }
    var visibilityMessage by remember {
        mutableStateOf(true)
    }
    var visibilityList by remember {
        mutableStateOf(false)
    }
    val initialPage = remember {
        mutableIntStateOf(1)
    }

    viewModel.movies.observe(lifeCycleOwner, Observer { it ->
        Log.e("Movies", "movies: $it")
        listMovies.addAll(it)
        visibilityList = true
    })
    viewModel.isLoading.observe(lifeCycleOwner, Observer {
        visibilityMessage = it
    })
    AnimatedVisibility(visible = visibilityMessage) {
        Text(text = "Cargando...", color = Color.Black)
    }
    AnimatedVisibility(visible = visibilityList, ) {
        MoviesList(listMovies, viewModel, initialPage)
    }

}

@Composable
fun MoviesList(listMovies: List<Movie>, viewModel: MoviesViewModel, page: MutableIntState) {

    LazyColumn {
        items(listMovies.size) {
            // Muestra el elemento en la posición actual de la lista
            Text(text = "Position: ${it}")
            Text(text = "Item ${listMovies[it].title}")

            // Si estamos cerca del final de la lista, agregamos más elementos
            // se ejecutará cada vez que cambie el estado del composable,
            LaunchedEffect(Unit) {
                if (it == listMovies.lastIndex) {
                    page.intValue = page.intValue + 1
                    viewModel.getMovies(page.intValue)
                }
            }
        }
    }
}