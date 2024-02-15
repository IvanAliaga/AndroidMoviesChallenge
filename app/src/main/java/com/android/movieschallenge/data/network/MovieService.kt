package com.android.movieschallenge.data.network

import com.android.movieschallenge.data.network.response.ObjectResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieService @Inject constructor(private val api: MovieDataApi) {
    suspend fun getMovies(page: Int): ObjectResponse {
        return withContext(Dispatchers.IO) {
            val response = api.getUpcoming(page)
            response.body() ?: ObjectResponse(0, emptyList())
        }
    }

}