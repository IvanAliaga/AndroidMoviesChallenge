package com.android.movieschallenge.presentation.movies

import com.android.movieschallenge.domain.model.Movie
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList


data class MoviesScreenState(
    val listMovie: ImmutableList<Movie>,
    val isLoading: Boolean,
) {
    companion object {
        fun default() = MoviesScreenState(
            listMovie = emptyList<Movie>().toImmutableList(),
            isLoading = false,
        )
    }
}
