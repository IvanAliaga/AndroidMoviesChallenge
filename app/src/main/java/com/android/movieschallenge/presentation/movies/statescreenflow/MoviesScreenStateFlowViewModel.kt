package com.android.movieschallenge.presentation.movies.statescreenflow

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.movieschallenge.domain.usecase.MoviesUseCase
import com.android.movieschallenge.presentation.movies.MoviesScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MoviesScreenStateFlowViewModel @Inject constructor(private val moviesUseCase: MoviesUseCase) : ViewModel() {
    private val _screenState = MutableStateFlow(MoviesScreenState.default())
    val screenState: StateFlow<MoviesScreenState> = _screenState

    init {
        getMovies(1)
    }

    fun getMovies(page: Int) {
        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            // Manejar la excepción aquí
            Log.e("MovieScreenStateFlowVM", "Error: ${throwable.message}")
            // Actualizar el estado de isLoading a false
            _screenState.update { it.copy(isLoading = false) }
        }

        viewModelScope.launch(exceptionHandler) {
            _screenState.update { it.copy(isLoading = true) }
            try {
                val currentList = _screenState.value.listMovie
                val result = withContext(Dispatchers.Default) {
                    moviesUseCase.getMovies(page)
                }
                val updatedList = (currentList + result).distinctBy { it.id }.toImmutableList()
                _screenState.update { it.copy(listMovie = updatedList, isLoading = false) }
            } catch (e: Exception) {
                // La excepción será manejada por el CoroutineExceptionHandler
                // No es necesario manejarlo aquí nuevamente
            }
        }
    }

}