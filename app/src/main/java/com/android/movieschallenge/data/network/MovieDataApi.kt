package com.android.movieschallenge.data.network

import com.android.movieschallenge.data.network.response.MovieResponse
import com.android.movieschallenge.data.network.response.ObjectResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDataApi {
    @GET("movie/upcoming")
    suspend fun getUpcoming(@Query("page") page: Int): Response<ObjectResponse>
    @GET("movie/{movie_id}")
    suspend fun getMovie(@Path("movie_id") id: Int): Response<MovieResponse>
}