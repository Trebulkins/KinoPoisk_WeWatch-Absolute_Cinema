package com.example.absolute_cinema_wewatch.add

class AddContract {
    interface PresenterInterface {
        fun addMovie (title: String , releaseDate: String , posterPath: String )
    }

    interface ViewInterface {
        fun returnToMain ()
        fun displayMessage (message: String)
        fun displayError (message: String)
    }
}