package com.android.movieschallenge.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.movieschallenge.data.database.dao.MovieDao
import com.android.movieschallenge.data.database.entity.MovieEntity

@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
abstract class MovieDatabase: RoomDatabase() {
    abstract fun getMovieDao():MovieDao
}