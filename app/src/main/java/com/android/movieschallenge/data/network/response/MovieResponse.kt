package com.android.movieschallenge.data.network.response

import com.google.gson.annotations.SerializedName

data class MovieResponse (
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("poster_path") val posterPath: String
) {
    constructor() : this(-1, "", 0.0, "", "", "")
}

