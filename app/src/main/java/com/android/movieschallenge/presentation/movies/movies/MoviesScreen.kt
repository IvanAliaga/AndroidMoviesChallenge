package com.android.movieschallenge.presentation.movies.movies

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.movieschallenge.presentation.LoaderFullScreen

@Stable
@Composable
fun MoviesScreen(toMovieDetail: (Int) -> Unit) {
    val viewModel: MoviesViewModel = hiltViewModel()
    val moviesState by viewModel.movies.observeAsState()
    val isLoadingState by viewModel.isLoading.observeAsState()

    val initialPage = remember {
        mutableIntStateOf(1)
    }

    Column {
        isLoadingState?.let {
            if(it) LoaderFullScreen()
        }
        moviesState?.let { movies ->
            MovieLazyColumn(movies, initialPage, toMovieDetail)
        }
    }
}