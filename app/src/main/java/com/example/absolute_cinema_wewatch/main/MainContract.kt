package com.example.absolute_cinema_wewatch.main

import com.example.absolute_cinema_wewatch.database.Movie

class MainContract {
    interface PresenterInterface {
        fun getMyMoviesList()
        fun stop()
    }

    interface ViewInterface {
        fun displayMovies (movieList: List <Movie>)
        fun displayNoMovies ()
        fun showToast (message: String)
        fun displayError (message: String)
    }
}