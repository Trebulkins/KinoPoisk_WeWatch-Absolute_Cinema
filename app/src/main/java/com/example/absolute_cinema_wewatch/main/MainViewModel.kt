package com.example.absolute_cinema_wewatch.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.absolute_cinema_wewatch.database.LocalDatabase
import com.example.absolute_cinema_wewatch.database.Movie
import com.example.absolute_cinema_wewatch.database.MovieRepository
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: MovieRepository
    val allMovies: LiveData<List<Movie>>
    val searchResults = MutableLiveData<List<Movie>>()

    init {
        val movieDao = LocalDatabase.getInstance(application).movieDao()
        repository = MovieRepository(movieDao)
        allMovies = repository.allMovies
    }

    fun insertMovie(Movie: Movie) = viewModelScope.launch {
        repository.insert(Movie)
    }

    fun deleteMovie(movieId: Int) = viewModelScope.launch {
        repository.delete(movieId)
    }
}