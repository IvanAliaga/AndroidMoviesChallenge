package com.android.movieschallenge.data.network

import com.android.movieschallenge.data.network.response.ResultResponse
import retrofit2.Response
import retrofit2.http.GET

interface MovieDataApi {
    @GET("upcoming")
    suspend fun getUpcoming(): Response<ResultResponse>
}