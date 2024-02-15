package com.android.movieschallenge.data.network

import com.android.movieschallenge.data.network.response.MovieResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieService @Inject constructor(private val api: MovieDataApi) {
    suspend fun getMovies(): List<MovieResponse> {
        return withContext(Dispatchers.IO) {
            val response = api.getUpcoming()
            response.body()?.results ?: emptyList()
        }
    }

}