package com.android.movieschallenge.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.movieschallenge.data.database.entity.MovieEntity

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie_table")
    suspend fun getAllMovies():List<MovieEntity>

    @Query("SELECT * FROM movie_table LIMIT 20 OFFSET :offset")
    suspend fun getPagedMovies(offset: Int): List<MovieEntity>

    @Query("SELECT * FROM movie_table WHERE id=:id")
    suspend fun getMovie(id: Int): MovieEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies:List<MovieEntity>)

    @Query("DELETE FROM movie_table")
    suspend fun deleteAllMovies()
}