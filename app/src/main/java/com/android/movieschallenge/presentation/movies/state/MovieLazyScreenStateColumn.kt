package com.android.movieschallenge.presentation.movies.state

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.movieschallenge.domain.model.Movie
import com.android.movieschallenge.presentation.movies.MovieCard
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Composable
fun MovieLazyScreenStateColumn (
    movieList: ImmutableList<Movie> = emptyList<Movie>().toImmutableList(),
    page: MutableIntState,
    toMovieDetail: (Int) -> Unit
){
    val viewModel: MoviesStateFlowMovieModel = hiltViewModel()
    val listState = rememberLazyListState()

    LazyColumn(
        state = listState,
        modifier = Modifier
            .systemBarsPadding()
            .imePadding()
    ) {
        items(count = movieList.size,
            key = {
                movieList[it].id
            }){

            MovieCard(modifier = Modifier.clickable {
                toMovieDetail(movieList[it].id)
            }, movie = movieList[it])

            // Si estamos cerca del final de la lista, agregamos más elementos
            // se ejecutará cada vez que cambie el estado del composable,
            LaunchedEffect(Unit) {
                if (it == movieList.lastIndex) {
                    page.intValue += 1
                    viewModel.getMovies(page.intValue)
                }
            }

        }
    }
}