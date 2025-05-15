package com.example.absolute_cinema_wewatch.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Update
import io.reactivex.Observable

@Dao
interface MovieDao {
  @get:Query("SELECT * FROM movie_table")
  val all: Observable<List<Movie>>

  @Query("SELECT * FROM movie_table")
  fun getAllMovies(): LiveData<List<Movie>>

  @Insert(onConflict = REPLACE)
  fun insert(movie: Movie)

  @Query("DELETE FROM movie_table WHERE id = :id")
  fun delete(id: Int?)

  @Query("DELETE FROM movie_table")
  fun deleteAll()

  @Update
  fun update(movie: Movie)
}