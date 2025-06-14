package com.example.absolute_cinema_wewatch.main

import android.util.Log
import com.example.absolute_cinema_wewatch.database.LocalDataSource
import com.example.absolute_cinema_wewatch.database.Movie
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class MainPresenter(private var viewInterface: MainContract.ViewInterface, private var dataSource: LocalDataSource): MainContract.PresenterInterface {
    private val TAG = "MainPresenter"
    private val compositeDisposable = CompositeDisposable()

    //1
    val myMoviesObservable: Observable<List<Movie>>
        get() = dataSource.allMovies

    //2
    val observer: DisposableObserver<List<Movie>>
        get() = object: DisposableObserver<List<Movie>>() {
            override fun onNext(movieList: List<Movie>) {
                if (movieList.isEmpty()) {
                    viewInterface.displayNoMovies()
                } else {
                    viewInterface.displayMovies(movieList)
                }
            }

            override fun onError(e: Throwable) {
                Log.d(TAG, "Error fetching movie list.", e)
                viewInterface.displayError("Error fetching movie list.")
            }

            override fun onComplete() {
                Log.d(TAG, "Completed")
            }
        }

    //3
    override fun getMyMoviesList() {
        val myMoviesDisposable = myMoviesObservable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(observer)
        compositeDisposable.add(myMoviesDisposable)
    }

    override fun onDeleteTapped(selectedMovies: HashSet<*>) {
        for (movie in selectedMovies) {
            dataSource.delete(movie as Movie)
        }
        if (selectedMovies.size == 1) {
            viewInterface.displayMessage("Movie deleted")
        } else if (selectedMovies.size > 1) {
            viewInterface.displayMessage("Movies deleted")
        }
    }

    override fun stop() {
        compositeDisposable.clear()
    }
}