package com.android.movieschallenge.data.network.response

import com.google.gson.annotations.SerializedName

data class ResultResponse (
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: List<MovieResponse>
)