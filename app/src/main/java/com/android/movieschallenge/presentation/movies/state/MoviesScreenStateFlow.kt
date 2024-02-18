package com.android.movieschallenge.presentation.movies.state

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.android.movieschallenge.domain.model.Movie
import com.android.movieschallenge.presentation.LoaderFullScreen
import kotlinx.collections.immutable.toImmutableList

@Composable
fun MoviesScreenStateFlow(toMovieDetail: (Int) -> Unit) {
    val viewModel: MoviesStateFlowMovieModel = hiltViewModel()
    val combinedState by viewModel.combinedState.collectAsStateWithLifecycle(
        initialValue = Pair(emptyList<Movie>().toImmutableList(), false),
        lifecycleOwner = LocalLifecycleOwner.current
    )

    val movies = combinedState.first
    val isLoading = combinedState.second

    val initialPage = remember {
        mutableIntStateOf(1)
    }

    Column {
        if(isLoading){
            LoaderFullScreen()
        }
        MovieLazyScreenStateColumn(movies, initialPage, toMovieDetail)

    }
}