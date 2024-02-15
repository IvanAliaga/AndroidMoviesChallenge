package com.android.movieschallenge.data.network

import com.android.movieschallenge.data.network.response.ObjectResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieDataApi {
    @GET("upcoming")
    suspend fun getUpcoming(@Query("page") page: Int): Response<ObjectResponse>
}