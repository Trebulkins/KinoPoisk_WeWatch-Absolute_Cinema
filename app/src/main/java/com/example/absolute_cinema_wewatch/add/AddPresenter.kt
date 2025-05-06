package com.example.absolute_cinema_wewatch.add

import com.example.absolute_cinema_wewatch.database.LocalDataSource

class AddPresenter(
    private var viewInterface: AddContract.ViewInterface,
    private var dataSource: LocalDataSource):
    AddContract.PresenterInterface {
        override fun addMovie(
            title: String,
            releaseDate: String,
            posterPath: String) {
        }
}