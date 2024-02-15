package com.android.movieschallenge.data.database.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.movieschallenge.data.database.entity.MovieEntity

interface MovieDao {
    @Query("SELECT * FROM movie_table ORDER BY title DESC")
    suspend fun getAllMovies():List<MovieEntity>

    @Query("SELECT * FROM movie_table ORDER BY id ASC LIMIT :limit OFFSET :offset")
    suspend fun getPagedMovies(limit: Int, offset: Int): List<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies:List<MovieEntity>)

    @Query("DELETE FROM movie_table")
    suspend fun deleteAllMovies()
}