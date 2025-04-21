package com.example.absolute_cinema_wewatch.database

import android.util.Log
import com.example.absolute_cinema_wewatch.api.KinoResponse
import com.example.absolute_cinema_wewatch.api.RetrofitClient
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers

import io.reactivex.schedulers.Schedulers

open class RemoteDataSource {
    private val TAG = "RemoteDataSource"

    fun searchResultsObservable(query: String): Observable<KinoResponse> {
        Log.d(TAG, "search/movie")
        return RetrofitClient.moviesApi
            .searchMovie(RetrofitClient.API_KEY, query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}