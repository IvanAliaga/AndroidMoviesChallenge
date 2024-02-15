package com.android.movieschallenge.data.database

import com.android.movieschallenge.data.database.dao.MovieDao
import com.android.movieschallenge.data.database.entity.MovieEntity
import com.android.movieschallenge.data.network.MovieService
import com.android.movieschallenge.data.network.response.MovieResponse
import com.android.movieschallenge.domain.model.Movie
import com.android.movieschallenge.domain.model.toDomain
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val api: MovieService,
    private val movieDao: MovieDao
) {
    suspend fun getAllMoviesFromApi(page: Int): List<Movie> {
        val response: List<MovieResponse> = api.getMovies(page).results
        return response.map { it.toDomain() }
    }

    suspend fun getAllMoviesFromDatabase(page: Int):List<Movie>{
        val offset: Int = 20 * (page - 1)
        val response: List<MovieEntity> = movieDao.getPagedMovies(offset)
        return response.map { it.toDomain() }
    }

    suspend fun insertMovies(quotes:List<MovieEntity>){
        movieDao.insertAll(quotes)
    }

    suspend fun clearMovies(){
        movieDao.deleteAllMovies()
    }
}