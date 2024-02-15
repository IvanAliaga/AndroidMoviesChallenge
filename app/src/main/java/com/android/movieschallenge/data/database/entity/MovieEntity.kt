package com.android.movieschallenge.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.movieschallenge.domain.model.Movie

@Entity(tableName = "movie_table")
data class MovieEntity(
        @PrimaryKey
        @ColumnInfo(name = "id") val id: Int,
        @ColumnInfo(name = "title") val title: String,
        @ColumnInfo(name = "vote_average") val voteAverage: Double,
        @ColumnInfo(name = "release_date") val releaseDate: Double,
        @ColumnInfo(name = "overview") val overview: String,
        @ColumnInfo(name = "poster_path") val posterPath: String
)

fun Movie.toDatabase() = MovieEntity(id = id, title =  title, voteAverage = voteAverage,
        releaseDate = releaseDate, overview = overview, posterPath = posterPath)