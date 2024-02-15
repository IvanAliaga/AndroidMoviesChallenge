package com.android.movieschallenge.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_table")
data class MovieEntity(
        @PrimaryKey
        @ColumnInfo(name = "id") val id: Int,
        @ColumnInfo(name = "title") val title: String,
        @ColumnInfo(name = "vote_average") val author: Double,
        @ColumnInfo(name = "release_date") val releaseDate: Double,
        @ColumnInfo(name = "overview") val overview: String,
        @ColumnInfo(name = "poster_path") val posterPath: String
)