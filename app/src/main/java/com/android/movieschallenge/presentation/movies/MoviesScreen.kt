package com.android.movieschallenge.presentation.movies

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.movieschallenge.domain.model.Movie
import com.android.movieschallenge.presentation.LoaderFullScreen
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Composable
fun MoviesScreen(toMovieDetail: (Int) -> Unit) {

    val viewModel: Movies2MovieModel = hiltViewModel()

    val moviesState by viewModel.movies.collectAsState()
    val isLoadingState by viewModel.isLoading.collectAsState()

    val initialPage = remember {
        mutableIntStateOf(1)
    }

    Column {
        if(isLoadingState) {
            LoaderFullScreen()
        }
        //MoviesList(listMovies, viewModel, initialPage, toMovieDetail)
        MovieLazyColumn(moviesState.toImmutableList(), initialPage.intValue, toMovieDetail)
    }

}

@Composable
fun MoviesList(
    listMovies: ImmutableList<Movie>,
    page: Int,
    toMovieDetail: (Int) -> Unit
) {
    val viewModel: MoviesViewModel = hiltViewModel()
    val listState = rememberLazyListState()
    LazyColumn(
        state = listState,
        modifier = Modifier
            .systemBarsPadding()
            .imePadding()
    ) {
        items(
            count = listMovies.size,
            key = {
                listMovies[it].id
            }) {

            MovieCard(modifier = Modifier.clickable {
                toMovieDetail(listMovies[it].id)
            }, movie = listMovies[it])


            // Si estamos cerca del final de la lista, agregamos más elementos
            // se ejecutará cada vez que cambie el estado del composable,
            LaunchedEffect(Unit) {
                if (it == listMovies.lastIndex) {
                    viewModel.getMovies(page + 1)
                }
            }
        }
    }
}