package com.android.movieschallenge.presentation.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.movieschallenge.domain.model.Movie
import com.android.movieschallenge.domain.usecase.MoviesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

open class Movies2MovieModel @Inject constructor(private val moviesUseCase: MoviesUseCase) : ViewModel() {
    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies: StateFlow<List<Movie>> = _movies

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
            val result = withContext(Dispatchers.Default) {
                moviesUseCase.getMovies(page)
            }
            _movies.value = result.distinctBy { it.id }
            _isLoading.value = false
        }
    }

    fun getMovie(id: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            val result = withContext(Dispatchers.Default) {
                moviesUseCase.getMovie(id)
            }
            _movie.value = result
            _isLoading.value = false
        }
    }


}