package com.android.movieschallenge.domain.model

import com.android.movieschallenge.data.database.entity.MovieEntity
import com.android.movieschallenge.data.network.response.MovieResponse

data class Movie (val id: Int,
                  val title: String,
                  val voteAverage: Double,
                  val releaseDate: Double,
                  val overview: String,
                  val posterPath: String)

fun MovieResponse.toDomain() = Movie(id, title, voteAverage, releaseDate, overview, posterPath)
fun MovieEntity.toDomain() = Movie(id, title, voteAverage, releaseDate, overview, posterPath)