package com.android.movieschallenge.presentation.movies

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.movieschallenge.domain.model.Movie
import com.android.movieschallenge.domain.usecase.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(private val moviesUseCase: GetMoviesUseCase) : ViewModel() {
    val movies = MutableLiveData<List<Movie>>()
    val isLoading = MutableLiveData<Boolean>()

    init {
        getMovies(1)
    }
    fun getMovies(page: Int){
        viewModelScope.launch() {
            isLoading.postValue(true)
            val result = withContext(Dispatchers.Default) {
                moviesUseCase.invoke(page)
            }
            movies.postValue(result.distinctBy { it.id }.sortedBy { it.title })
            isLoading.postValue(false)
        }
    }
}