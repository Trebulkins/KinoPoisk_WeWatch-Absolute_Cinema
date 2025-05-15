package com.example.absolute_cinema_wewatch.database

import androidx.lifecycle.LiveData

class MovieRepository(private val movieDao: MovieDao) {
    val allMovies: LiveData<List<Movie>> = movieDao.getAllMovies()

    suspend fun insert(movie: Movie) {
        movieDao.insert(movie)
    }

    suspend fun delete(movieId: Int) {
        movieDao.delete(movieId)
    }
}