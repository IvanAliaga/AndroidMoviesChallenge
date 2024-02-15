package com.android.movieschallenge.data.network

import com.android.movieschallenge.data.model.MovieModel
import retrofit2.http.GET

interface MovieDataApi {
    @GET("/upcoming")
    suspend fun getUpcoming(): ArrayList<MovieModel>
}