package com.example.absolute_cinema_wewatch.add

import com.example.absolute_cinema_wewatch.database.LocalDataSource
import com.example.absolute_cinema_wewatch.database.Movie

class AddPresenter(
    private var viewInterface: AddContract.ViewInterface,
    private var dataSource: LocalDataSource):
    AddContract.PresenterInterface {
    override fun addMovie(title: String, releaseDate: String, posterPath: String) {
        if (title.isEmpty()) {
            viewInterface.displayError ("Название фильма не может быть пустым")
        } else {
            val movie = Movie(nameRu = title, year = releaseDate.toInt(), posterUrl = posterPath)
            dataSource.insert(movie)
            viewInterface.returnToMain()
        }
    }
}