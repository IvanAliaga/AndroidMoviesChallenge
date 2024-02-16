package com.android.movieschallenge.domain.usecase

import com.android.movieschallenge.data.database.MovieRepository
import com.android.movieschallenge.data.database.entity.toDatabase
import com.android.movieschallenge.domain.model.Movie
import javax.inject.Inject

class MoviesUseCase @Inject constructor(private val repository: MovieRepository) {
    suspend fun getMovies(page: Int): List<Movie> {
        /*
        * Se prioriza que la data sea del api
        * si no existe un response por parte del api
        * pasa a obtener de la base de datos interna
        * de lo contrario la data obtenida es insertada
        * */
        val movies = repository.getAllMoviesFromApi(page)
        return if (movies.isEmpty()) {
            repository.getAllMoviesFromDatabase(page)
        } else {
            repository.insertMovies(movies.map { it.toDatabase() })
            movies
        }
    }

    suspend fun getMovie(id: Int): Movie {
        val movie = repository.getMovieFromApi(id)
        return if (movie.id == -1) {
            repository.getMovieFromDatabase(id)
        } else {
            movie
        }
    }
}
