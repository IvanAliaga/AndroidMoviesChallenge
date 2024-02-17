package com.android.movieschallenge.presentation.movies

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.movieschallenge.domain.model.Movie
import kotlinx.collections.immutable.ImmutableList

@Composable
fun MovieLazyColumn (
    movieList: ImmutableList<Movie>,
    page: Int,
    toMovieDetail: (Int) -> Unit
){
    val viewModel: Movies2MovieModel = hiltViewModel()
    val listState = rememberLazyListState()
    LazyColumn(
        state = listState,
        modifier = Modifier
            .systemBarsPadding()
            .imePadding()
    ) {
        items(count = movieList.size,
            key = {
                movieList[it].idUUID
            }){

            MovieCard(modifier = Modifier.clickable {
                toMovieDetail(movieList[it].id)
            }, movie = movieList[it])

            // Si estamos cerca del final de la lista, agregamos más elementos
            // se ejecutará cada vez que cambie el estado del composable,
            LaunchedEffect(Unit) {
                if (it == movieList.lastIndex) {
                    viewModel.getMovies(page + 1)
                }
            }

        }
    }
}