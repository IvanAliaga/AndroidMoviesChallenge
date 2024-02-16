package com.android.movieschallenge.presentation.movies

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.android.movieschallenge.domain.model.Movie
import com.android.movieschallenge.presentation.LoaderFullScreen

@Composable
fun MoviesScreen(lifeCycleOwner: LifecycleOwner, viewModel: MoviesViewModel = hiltViewModel(), toMovieDetail: (Int) -> Unit) {
    val listMovies = remember { mutableStateListOf<Movie>() }

    var visibilityList by remember {
        mutableStateOf(false)
    }
    val initialPage = remember {
        mutableIntStateOf(1)
    }

    var isLoading by remember { mutableStateOf(true) }

    viewModel.movies.observe(lifeCycleOwner, Observer { it ->
        listMovies.addAll(it)
        visibilityList = true
    })
    viewModel.isLoading.observe(lifeCycleOwner, Observer {
        isLoading = it
    })

    Column {
        if(isLoading) {
            LoaderFullScreen()
        } else {
            MoviesList(listMovies, viewModel, initialPage, toMovieDetail)
        }
    }

}

@Composable
fun MoviesList(
    listMovies: List<Movie>,
    viewModel: MoviesViewModel,
    page: MutableIntState,
    toMovieDetail: (Int) -> Unit
) {
    val listState = rememberLazyListState()
    LazyColumn(
        state = listState,
        modifier = Modifier
            .systemBarsPadding()
            .imePadding()
    ) {
        items(listMovies.size) {

            MovieCard(modifier = Modifier.clickable {
                toMovieDetail(listMovies[it].id)
            }, movie = listMovies[it])


            // Si estamos cerca del final de la lista, agregamos más elementos
            // se ejecutará cada vez que cambie el estado del composable,
            LaunchedEffect(Unit) {
                if (it == listMovies.lastIndex) {
                    page.intValue = page.intValue + 1
                    viewModel.getMovies(page.intValue)
                    listState.scrollToItem(listMovies.size - 1)
                }
            }
        }
    }
}