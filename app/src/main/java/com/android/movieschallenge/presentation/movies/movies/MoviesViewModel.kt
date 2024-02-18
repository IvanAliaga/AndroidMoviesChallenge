package com.android.movieschallenge.presentation.movies.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.movieschallenge.domain.model.Movie
import com.android.movieschallenge.domain.usecase.MoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(private val moviesUseCase: MoviesUseCase) : ViewModel() {
    private val _movies = MutableLiveData<ImmutableList<Movie>>()
    val movies: LiveData<ImmutableList<Movie>> = _movies

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    val movie = MutableLiveData<Movie>()


    init {
        getMovies(1)
    }
    fun getMovies(page: Int){
        viewModelScope.launch() {
            _isLoading.value = true
            val result = moviesUseCase.getMovies(page)
            val currentMovies = _movies.value ?: emptyList()
            val updatedMovies = currentMovies.toMutableList()
            updatedMovies.addAll(result.distinctBy { it.id })
            _movies.value = updatedMovies.toImmutableList()
            _isLoading.value = false
        }
    }

    fun getMovie(id: Int){
        viewModelScope.launch(){
            _isLoading.value = true
            val result = withContext(Dispatchers.Default) {
                moviesUseCase.getMovie(id)
            }
            movie.postValue(result)
            _isLoading.value = false
        }
    }
}