package com.example.absolute_cinema_wewatch.search

import android.util.Log
import com.example.absolute_cinema_wewatch.api.KinoResponse
import com.example.absolute_cinema_wewatch.database.RemoteDataSource
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class SearchPresenter(
    private var viewInterface: SearchContract.ViewInterface,
    private var dataSource: RemoteDataSource) : SearchContract.PresenterInterface {
        private val TAG = "SearchPresenter"
    val searchResultsObservable: (String) -> Observable<KinoResponse> = {
        query -> dataSource.searchResultsObservable(query)
    }
    //2
    val observer: DisposableObserver<KinoResponse>
        get() = object : DisposableObserver<KinoResponse>() {
            override fun onNext(@NonNull tmdbResponse: KinoResponse) {
                Log.d(TAG, "OnNext" + tmdbResponse.totalResults)
                viewInterface.displayResult(tmdbResponse)
            }
            override fun onError(@NonNull e: Throwable) {
                Log.d(TAG, "Error fetching movie data.", e)
                viewInterface.displayError("Error fetching movie data.")
            }
            override fun onComplete() {
                Log.d(TAG, "Completed")
            }
        }
    //3
    private val compositeDisposable = CompositeDisposable()
    override fun getSearchResults(query: String) {
        val searchResultsDisposable = searchResultsObservable(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(observer)
        compositeDisposable.add(searchResultsDisposable)
    }

    override fun stop() {
        compositeDisposable.clear()
    }

}