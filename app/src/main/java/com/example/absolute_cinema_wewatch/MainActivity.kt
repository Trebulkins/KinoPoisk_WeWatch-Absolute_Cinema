package com.example.absolute_cinema_wewatch

import android.os.Bundle
import android.util.Log
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.absolute_cinema_wewatch.database.LocalDataSource
import com.example.absolute_cinema_wewatch.database.Movie
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    private lateinit var moviesRecyclerView: RecyclerView
    private lateinit var fab: FloatingActionButton
    private lateinit var noMoviesLayout: LinearLayout
    private var adapter: MainAdapter? = null

    private lateinit var dataSource: LocalDataSource
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViews()
    }

    private fun setupViews() {
        moviesRecyclerView = findViewById(R.id.movies_recyclerview)
        moviesRecyclerView.layoutManager = LinearLayoutManager(this)
        fab = findViewById(R.id.fab)
        noMoviesLayout = findViewById(R.id.no_movies_layout)
        supportActionBar?.title = "@string/movies_to_watch"
    }

    override fun onStart() {
        super.onStart()
        dataSource = LocalDataSource(application)
        getMyMoviesList()
    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
    }

    private fun getMyMoviesList() {
        val myMoviesDisposable = myMoviesObservable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(observer)

        compositeDisposable.add(myMoviesDisposable)
    }
    private val myMoviesObservable: Observable<List<Movie>> get() = dataSource.allMovies

    private val observer: DisposableObserver<List<Movie>> get() = object : DisposableObserver<List<Movie>>() {
        override fun onNext(movieList: List<Movie>) {
            displayMovies(movieList)
        }

        override fun onError(@NonNull e: Throwable) {
            Log.d(TAG, "Error$e")
            e.printStackTrace()
            displayError("@string/error_fetching_movie_list")
        }

        override fun onComplete() {
            Log.d(TAG, "@string/completed")
        }
    }

    fun displayMovies(movieList: List<Movie>?) {
        if (movieList.isNullOrEmpty()) {
            Log.d(TAG, "No movies to display")
            moviesRecyclerView.visibility = INVISIBLE
            noMoviesLayout.visibility = VISIBLE
        } else {
            adapter = MainAdapter(movieList, this@MainActivity)
            moviesRecyclerView.adapter = adapter

            moviesRecyclerView.visibility = VISIBLE
            noMoviesLayout.visibility = INVISIBLE
        }
    }

    fun showToast(str: String) {
        Toast.makeText(this@MainActivity, str, Toast.LENGTH_LONG).show()
    }

    fun displayError(e: String) {
        showToast(e)
    }
}