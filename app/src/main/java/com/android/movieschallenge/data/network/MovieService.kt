package com.android.movieschallenge.data.network

import android.util.Log
import com.android.movieschallenge.data.network.response.ObjectResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieService @Inject constructor(private val api: MovieDataApi) {
    suspend fun getMovies(page: Int): ObjectResponse {
        return withContext(Dispatchers.IO) {
            var objectResponse = ObjectResponse(0, emptyList())
            try {
                val response = api.getUpcoming(page)
                if(response.isSuccessful){
                    objectResponse = response.body()!!
                }
            } catch (e: Exception){
                Log.e("MovieService", e.message.toString() + " " + (e.localizedMessage?.toString()))
            }
            objectResponse
        }
    }

}