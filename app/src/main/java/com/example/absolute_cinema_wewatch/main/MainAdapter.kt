package com.example.absolute_cinema_wewatch.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.absolute_cinema_wewatch.R
import com.example.absolute_cinema_wewatch.api.RetrofitClient
import com.example.absolute_cinema_wewatch.database.Movie
import com.squareup.picasso.Picasso
import java.util.HashSet

class MainAdapter(internal var movieList: List<Movie>, internal var context: Context) : RecyclerView.Adapter<MainAdapter.MoviesHolder>() {
    // HashMap to keep track of which items were selected for deletion
    val selectedMovies = HashSet<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.item_movie_main, parent, false)
        return MoviesHolder(v)
    }

    override fun onBindViewHolder(holder: MoviesHolder, position: Int) {
        holder.titleTextView.text = movieList[position].nameRu?:movieList[position].nameOriginal
        holder.releaseDateTextView.text = movieList[position].year.toString()
        if (movieList[position].posterUrl.equals("")) {
            holder.movieImageView.setImageDrawable(context.getDrawable(R.drawable.ic_local_movies_gray))
        } else {
            Picasso.get().load(RetrofitClient.TMDB_BASE_URL + movieList[position].posterUrl).into(holder.movieImageView)
        }
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    inner class MoviesHolder(v: View) : RecyclerView.ViewHolder(v) {
        internal var titleTextView: TextView = v.findViewById(R.id.title_textview)
        internal var releaseDateTextView: TextView = v.findViewById(R.id.release_date_textview)
        internal var movieImageView: ImageView = v.findViewById(R.id.movie_imageview)
        internal var checkBox: CheckBox = v.findViewById(R.id.checkbox)

        init {
            checkBox.setOnClickListener {
                val adapterPosition = adapterPosition
                if (!selectedMovies.contains(movieList[adapterPosition])) {
                    checkBox.isChecked = true
                    selectedMovies.add(movieList[adapterPosition])
                } else {
                    checkBox.isChecked = false
                    selectedMovies.add(movieList[adapterPosition])
                }
            }
        }

    }
    }