package com.example.absolute_cinema_wewatch.api

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface RetrofitInterface {
    @GET("films")
    fun searchMovie(@Header("X-API-KEY") api_key: String, @Query("keyword") s: String): Observable<KinoResponse>
}