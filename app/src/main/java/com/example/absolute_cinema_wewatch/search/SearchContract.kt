package com.example.absolute_cinema_wewatch.search

import com.example.absolute_cinema_wewatch.api.KinoResponse

class SearchContract {
    interface PresenterInterface {
        fun getSearchResults(query: String)
        fun stop()
    }
    interface ViewInterface {
        fun displayResult(kinoResponse: KinoResponse)
        fun displayMessage(message: String)
        fun displayError(message: String)
    }
}