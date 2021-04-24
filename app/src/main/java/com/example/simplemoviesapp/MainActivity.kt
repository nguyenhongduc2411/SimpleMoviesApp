package com.example.simplemoviesapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.simplemoviesapp.adapters.MoviesAdapter
import com.example.simplemoviesapp.models.Movie
import com.example.simplemoviesapp.repositories.MoviesRepository
import com.example.simplemoviesapp.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var upcomingMovies: RecyclerView
    private lateinit var upcomingMoviesAdapter: MoviesAdapter
    private lateinit var upcomingMoviesLayoutMgr: LinearLayoutManager

    private lateinit var mMainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        upcomingMovies = findViewById(R.id.upcoming_movies)

        mMainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mMainViewModel.movies.observe(this, Observer {
            upcomingMoviesAdapter.appendMovies(it)
            attachUpcomingMoviesOnScrollListener()
        })

        initRecyclerView()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun initRecyclerView() {
        upcomingMoviesLayoutMgr = LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false
        )
        upcomingMovies.layoutManager = upcomingMoviesLayoutMgr

        val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        itemDecoration.setDrawable(getDrawable(R.drawable.divider)!!)
        upcomingMovies.addItemDecoration(itemDecoration)

        upcomingMoviesAdapter = MoviesAdapter()
        upcomingMovies.adapter = upcomingMoviesAdapter
    }

    private fun attachUpcomingMoviesOnScrollListener() {
        upcomingMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = upcomingMoviesLayoutMgr.itemCount
                val visibleItemCount = upcomingMoviesLayoutMgr.childCount
                val firstVisibleItem = upcomingMoviesLayoutMgr.findFirstVisibleItemPosition()

                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    upcomingMovies.removeOnScrollListener(this)
                    mMainViewModel.getUpcomingMovies()
                }
            }
        })
    }
}