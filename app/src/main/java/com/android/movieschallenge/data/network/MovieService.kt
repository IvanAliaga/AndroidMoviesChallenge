package com.android.movieschallenge.data.network

import android.util.Log
import androidx.compose.runtime.Stable
import com.android.movieschallenge.data.network.response.MovieResponse
import com.android.movieschallenge.data.network.response.ObjectResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@Stable
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
                Log.e("MovieService(getMovies)", e.message.toString() + " " + (e.localizedMessage?.toString()))
            }
            objectResponse
        }
    }

    suspend fun getMovie(id: Int): MovieResponse {
        return withContext(Dispatchers.IO){
            var movieResponse = MovieResponse()
            try{
                val response = api.getMovie(id)
                if(response.isSuccessful){
                    movieResponse = response.body()!!
                }
            }catch (e: Exception){
                Log.e("MovieService(getMovie)", e.message.toString() + " " + (e.localizedMessage?.toString()))
            }
            movieResponse
        }
    }

}