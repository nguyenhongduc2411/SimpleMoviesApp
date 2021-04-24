package com.example.simplemoviesapp.viewmodels

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.simplemoviesapp.R
import com.example.simplemoviesapp.models.Movie
import com.example.simplemoviesapp.repositories.MoviesRepository

class MainViewModel : ViewModel() {
    private val mRepo = MoviesRepository
    val movies: LiveData<List<Movie>> get() = mRepo.movies //getter
    private var upcomingMoviesPage = 1

    init {
        MoviesRepository.getUpcomingMovies(upcomingMoviesPage)
    }

    fun getUpcomingMovies() {
        upcomingMoviesPage++
        MoviesRepository.getUpcomingMovies(upcomingMoviesPage)
    }
}