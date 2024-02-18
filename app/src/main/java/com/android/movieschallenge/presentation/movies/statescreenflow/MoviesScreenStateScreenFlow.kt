package com.android.movieschallenge.presentation.movies.statescreenflow

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.android.movieschallenge.presentation.LoaderFullScreen

@Composable
fun MoviesScreenStateScreenFlow(toMovieDetail: (Int) -> Unit) {
    val viewModel: MoviesScreenStateFlowViewModel = hiltViewModel()
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()

    val initialPage = remember {
        mutableIntStateOf(1)
    }

    Column {
        if(screenState.isLoading){
            LoaderFullScreen()
        }
        if(!screenState.listMovie.isEmpty()){
            MovieLazyScreenStateScreenColumn(screenState.listMovie, initialPage, toMovieDetail)
        }
    }
}