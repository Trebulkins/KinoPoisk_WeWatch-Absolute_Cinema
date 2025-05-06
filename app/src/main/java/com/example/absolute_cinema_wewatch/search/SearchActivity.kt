package com.example.absolute_cinema_wewatch.search

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.absolute_cinema_wewatch.R
import com.example.absolute_cinema_wewatch.api.KinoResponse
import com.example.absolute_cinema_wewatch.database.RemoteDataSource
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class SearchActivity : AppCompatActivity(), SearchContract.ViewInterface {
    private val TAG = "SearchActivity"
    private lateinit var searchResultsRecyclerView: RecyclerView
    private lateinit var adapter: SearchAdapter
    private lateinit var noMoviesTextView: TextView
    private lateinit var progressBar: ProgressBar
    private var query = ""

    private lateinit var searchPresenter: SearchPresenter
    private fun setupPresenter() {
        val dataSource = RemoteDataSource()
        searchPresenter = SearchPresenter(this, dataSource)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_movie)
        searchResultsRecyclerView = findViewById(R.id.search_results_recyclerview)
        noMoviesTextView = findViewById(R.id.no_movies_textview)
        progressBar = findViewById(R.id.progress_bar)

        val i = intent
        query = i.getStringExtra(SEARCH_QUERY) ?: ""
        setupViews()
        setupPresenter()
    }

    override fun onStart() {
        super.onStart()
        progressBar.visibility = VISIBLE
        searchPresenter.getSearchResults(query)
    }

    override fun onStop() {
        super.onStop()
        searchPresenter.stop()
    }

    private fun setupViews() {
        searchResultsRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun displayResult(kinoResponse: KinoResponse) {
        progressBar.visibility = INVISIBLE

        if (kinoResponse.totalResults == null || kinoResponse.totalResults == 0) {
            searchResultsRecyclerView.visibility = INVISIBLE
            noMoviesTextView.visibility = VISIBLE
        } else {
            adapter = SearchAdapter(kinoResponse.results
                ?: arrayListOf(), this@SearchActivity, itemListener)
            searchResultsRecyclerView.adapter = adapter

            searchResultsRecyclerView.visibility = VISIBLE
            noMoviesTextView.visibility = INVISIBLE
        }
    }

    override fun displayMessage(message: String) {
        Toast.makeText(this@SearchActivity, message, Toast.LENGTH_LONG).show()
    }

    override fun displayError(message: String) {
        displayMessage(message)
    }

    companion object {
        val SEARCH_QUERY = "searchQuery"
        val EXTRA_TITLE = "SearchActivity.TITLE_REPLY"
        val EXTRA_ORIGINAL_TITLE = "SearchActivity.ORIGINAL_TITLE_REPLY"
        val EXTRA_RELEASE_DATE = "SearchActivity.RELEASE_DATE_REPLY"
        val EXTRA_POSTER_PATH = "SearchActivity.POSTER_PATH_REPLY"
    }

    /**
     * Listener for clicks on tasks in the ListView.
     */
    internal var itemListener: RecyclerItemListener = object : RecyclerItemListener {
        override fun onItemClick(view: View, position: Int) {
            val movie = adapter.getItemAtPosition(position)

            val replyIntent = Intent()
            replyIntent.putExtra(EXTRA_TITLE, movie.nameRu)
            replyIntent.putExtra(EXTRA_ORIGINAL_TITLE, movie.nameOriginal)
            replyIntent.putExtra(EXTRA_RELEASE_DATE, movie.year.toString())
            replyIntent.putExtra(EXTRA_POSTER_PATH, movie.posterUrl)
            setResult(Activity.RESULT_OK, replyIntent)

            finish()
        }
    }

    interface RecyclerItemListener {
        fun onItemClick(view: View, position: Int)
    }

}