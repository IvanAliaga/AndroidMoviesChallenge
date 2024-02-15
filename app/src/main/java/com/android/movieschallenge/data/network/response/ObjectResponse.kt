package com.android.movieschallenge.data.network.response

import com.google.gson.annotations.SerializedName

data class ObjectResponse (
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: List<MovieResponse>
)