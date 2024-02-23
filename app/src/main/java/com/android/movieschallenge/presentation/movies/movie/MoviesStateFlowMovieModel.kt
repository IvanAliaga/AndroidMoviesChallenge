package com.android.movieschallenge.presentation.movies.movie

import android.util.Log
import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.movieschallenge.domain.model.Movie
import com.android.movieschallenge.domain.usecase.MoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
@Stable
@HiltViewModel
open class MoviesStateFlowMovieModel @Inject constructor(private val moviesUseCase: MoviesUseCase) : ViewModel() {
    private val _movies = MutableStateFlow(emptyList<Movie>().toImmutableList())
    val movies: StateFlow<ImmutableList<Movie>> = _movies

    private val _movie = MutableStateFlow<Movie?>(null)
    val movie: StateFlow<Movie?> = _movie

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        getMovies(1)
    }

    fun getMovies(page: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.Default) {
                    moviesUseCase.getMovies(page)
                }
                _movies.update { currentList ->
                    val newMovies = result.filterNot { movie ->
                        currentList.any { it.id == movie.id }
                    }
                    (currentList + newMovies).toImmutableList()
                }
            } catch (e: Exception) {
                Log.e("MoviesStateFlowMovieModel", e.message.toString() + " " + e.localizedMessage.toString())
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getMovie(id: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.Default) {
                    moviesUseCase.getMovie(id)
                }
                _movie.value = result
            } catch (e: Exception) {
                // Manejar errores específicos si es necesario
            } finally {
                _isLoading.value = false
            }
        }
    }


    val combinedState: Flow<Pair<ImmutableList<Movie>, Boolean>> = combine(
        _movies,
        _isLoading
    ) { movies, isLoading ->
        Pair(movies, isLoading)
    }


}