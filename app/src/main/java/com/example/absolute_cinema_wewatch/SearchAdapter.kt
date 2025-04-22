package com.example.absolute_cinema_wewatch

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.absolute_cinema_wewatch.api.RetrofitClient
import com.example.absolute_cinema_wewatch.database.Movie
import com.squareup.picasso.Picasso


class SearchAdapter(var movieList: List<Movie>, var context: Context, var listener: SearchActivity.RecyclerItemListener) : RecyclerView.Adapter<SearchAdapter.SearchMoviesHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchMoviesHolder {
    val view = LayoutInflater.from(context).inflate(R.layout.item_movie_details, parent, false)

    val viewHolder = SearchMoviesHolder(view)
    view.setOnClickListener { v -> listener.onItemClick(v, viewHolder.adapterPosition) }
    return viewHolder
  }

  override fun onBindViewHolder(holder: SearchMoviesHolder, position: Int) {

    holder.titleTextView.text = movieList[position].nameRu
    holder.releaseDateTextView.text = movieList[position].year.toString()

    if (movieList[position].posterUrl != null) {
      Picasso.get().load(RetrofitClient.TMDB_BASE_URL + movieList[position].posterUrl).into(holder.movieImageView)
    }
  }

  override fun getItemCount(): Int {
    return movieList.size
  }

  fun getItemAtPosition(pos: Int): Movie {
    return movieList[pos]
  }

  inner class SearchMoviesHolder(v: View) : RecyclerView.ViewHolder(v) {

    var titleTextView: TextView = v.findViewById(R.id.title_textview)
    var overviewTextView: TextView = v.findViewById(R.id.overview_overview)
    var releaseDateTextView: TextView = v.findViewById(R.id.release_date_textview)
    var movieImageView: ImageView = v.findViewById(R.id.movie_imageview)

    init {
      v.setOnClickListener { v: View ->
        listener.onItemClick(v, adapterPosition)
      }
    }
  }
}
