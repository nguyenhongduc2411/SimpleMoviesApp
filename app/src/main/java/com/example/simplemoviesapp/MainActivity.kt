package com.example.simplemoviesapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.simplemoviesapp.adapters.MoviesAdapter
import com.example.simplemoviesapp.databinding.ActivityMainBinding
import com.example.simplemoviesapp.models.Movie
import com.example.simplemoviesapp.repositories.MoviesRepository
import com.example.simplemoviesapp.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {
//    private lateinit var upcomingMovies: RecyclerView
    private lateinit var upcomingMoviesAdapter: MoviesAdapter
    private lateinit var upcomingMoviesLayoutMgr: LinearLayoutManager

    private lateinit var mMainViewModel: MainViewModel

    private lateinit var mDataBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        upcomingMovies = findViewById(R.id.upcoming_movies)

        mMainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        mMainViewModel.getUpcomingMovies(::onError)

        mMainViewModel.movies.observe(this, Observer {
            upcomingMoviesAdapter.appendMovies(it)
            attachUpcomingMoviesOnScrollListener()
        })

        mDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mDataBinding.apply {
            this.lifecycleOwner = this@MainActivity
        }

        initRecyclerView()
    }

    private fun onError() {
        Toast.makeText(this, getString(R.string.error_fetch_movies), Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun initRecyclerView() {
        upcomingMoviesLayoutMgr = LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false
        )
        mDataBinding.upcomingMovies.layoutManager = upcomingMoviesLayoutMgr

        val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        itemDecoration.setDrawable(getDrawable(R.drawable.divider)!!)
        mDataBinding.upcomingMovies.addItemDecoration(itemDecoration)

        upcomingMoviesAdapter = MoviesAdapter()
        mDataBinding.upcomingMovies.adapter = upcomingMoviesAdapter
    }

    private fun attachUpcomingMoviesOnScrollListener() {
        mDataBinding.upcomingMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = upcomingMoviesLayoutMgr.itemCount
                val visibleItemCount = upcomingMoviesLayoutMgr.childCount
                val firstVisibleItem = upcomingMoviesLayoutMgr.findFirstVisibleItemPosition()

                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    mDataBinding.upcomingMovies.removeOnScrollListener(this)
                    mMainViewModel.getUpcomingMovies(::onError)
                }
            }
        })
    }
}