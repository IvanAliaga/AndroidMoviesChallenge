package com.android.movieschallenge.domain.model

import androidx.compose.runtime.Immutable
import com.android.movieschallenge.data.database.entity.MovieEntity
import com.android.movieschallenge.data.network.response.MovieResponse

@Immutable
data class Movie (val id: Int,
                  val title: String,
                  val voteAverage: Double,
                  val releaseDate: String,
                  val overview: String,
                  val posterPath: String,
                  val idUUID: String = "")

fun MovieResponse.toDomain() = Movie(id, title, voteAverage, releaseDate, overview, posterPath)
fun MovieEntity.toDomain() = Movie(id, title, voteAverage, releaseDate, overview, posterPath)