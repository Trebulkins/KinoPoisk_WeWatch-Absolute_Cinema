package com.example.absolute_cinema_wewatch.database

import androidx.room.Entity
import androidx.room.PrimaryKey

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movie_table")
data class Movie(
    @PrimaryKey
    @SerializedName("id")
    @Expose
    var id: Int? = null,

    @SerializedName("ratingKinopoisk")
    @Expose
    var ratingKinopoisk: Float? = null,

    @SerializedName("nameRu")
    @Expose
    var nameRu: String? = null,

    @SerializedName("nameOriginal")
    @Expose
    var nameOriginal: String? = null,

    @SerializedName("posterUrl")
    @Expose
    var posterUrl: String? = null,

    @SerializedName("year")
    @Expose
    var year: Int? = null,

    var watched: Boolean = false
)
