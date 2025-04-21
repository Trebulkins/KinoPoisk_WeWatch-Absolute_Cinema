package com.example.absolute_cinema_wewatch.api

import com.example.absolute_cinema_wewatch.database.Movie
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class KinoResponse {
    @SerializedName("total")
    @Expose
    var totalResults: Int? = null

    @SerializedName("totalPages")
    @Expose
    var totalPages: Int? = null

    @SerializedName("items")
    @Expose
    var results: List<Movie>? = null
}