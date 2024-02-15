package com.android.movieschallenge.data.model

import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName

data class MovieModel (
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("vote_average") val author: Double,
    @SerializedName("release_date") val releaseDate: Double,
    @SerializedName("overview") val overview: String,
    @SerializedName("poster_path") val posterPath: String
)

