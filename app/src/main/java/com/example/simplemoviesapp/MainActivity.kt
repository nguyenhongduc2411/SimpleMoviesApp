package com.example.simplemoviesapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.simplemoviesapp.adapters.MoviesAdapter
import com.example.simplemoviesapp.models.Movie
import com.example.simplemoviesapp.repositories.MoviesRepository

class MainActivity : AppCompatActivity() {
    private lateinit var upcomingMovies: RecyclerView
    private lateinit var upcomingMoviesAdapter: MoviesAdapter
    private lateinit var upcomingMoviesLayoutMgr: LinearLayoutManager

    private var upcomingMoviesPage = 1

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        upcomingMovies = findViewById(R.id.upcoming_movies)
        upcomingMoviesLayoutMgr = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        upcomingMovies.layoutManager = upcomingMoviesLayoutMgr

        val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        itemDecoration.setDrawable(getDrawable(R.drawable.divider)!!)
        upcomingMovies.addItemDecoration(itemDecoration)

        upcomingMoviesAdapter = MoviesAdapter(mutableListOf())
        upcomingMovies.adapter = upcomingMoviesAdapter

        getUpcomingMovies()
    }

    private fun onUpcomingMoviesFetched(movies: List<Movie>) {
        //Log.d("MainActivity", "Movies: $movies")
        upcomingMoviesAdapter.appendMovies(movies)
        attachUpcomingMoviesOnScrollListener()
    }

    private fun onError() {
        Toast.makeText(this, getString(R.string.error_fetch_movies), Toast.LENGTH_SHORT).show()
    }

    private fun getUpcomingMovies() {
        MoviesRepository.getUpcomingMovies(
            upcomingMoviesPage,
            onSuccess = ::onUpcomingMoviesFetched,
            onError = ::onError
        )

        //Alternative way:
//        MoviesRepository.getUpcomingMovies(
//                onSuccess = { movies ->
//                    Log.d("MainActivity", "Movies: $movies")
//                },
//                onError = {
//                    Toast.makeText(this, getString(R.string.error_fetch_movies), Toast.LENGTH_SHORT).show()
//                }
//        )
    }

    private fun attachUpcomingMoviesOnScrollListener() {
        upcomingMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = upcomingMoviesLayoutMgr.itemCount
                val visibleItemCount = upcomingMoviesLayoutMgr.childCount
                val firstVisibleItem = upcomingMoviesLayoutMgr.findFirstVisibleItemPosition()

                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    upcomingMovies.removeOnScrollListener(this)
                    upcomingMoviesPage++
                    getUpcomingMovies()
                }
            }
        })
    }
}