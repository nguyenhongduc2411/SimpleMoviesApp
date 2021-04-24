package com.example.simplemoviesapp.repositories

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.simplemoviesapp.R
import com.example.simplemoviesapp.apis.Api
import com.example.simplemoviesapp.models.GetMoviesResponse
import com.example.simplemoviesapp.models.Movie
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//Singleton
object MoviesRepository {
    private val api: Api
    private var mMoviesList: List<Movie> = listOf()
    private val mMovies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> get() = mMovies //getter

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(Api::class.java)
    }

//    fun getPopularMovies(
//            page: Int = 1,
//            onSuccess: (movies: List<Movie>) -> Unit,
//            onError: () -> Unit) {
//        api.getPopularMovies(page = page)
//            .enqueue(object : Callback<GetMoviesResponse> {
//                override fun onResponse(
//                        call: Call<GetMoviesResponse>,
//                        response: Response<GetMoviesResponse>
//                ) {
//                    if (response.isSuccessful) {
//                        val responseBody = response.body()
//
//                        if (responseBody != null) {
////                            onSuccess.invoke(responseBody.movies)
//                            mMoviesList = responseBody.movies
//                            mMovies.value = mMoviesList
//                        } else {
//                            onError.invoke()
//                        }
//                    }
//                    else {
//                        onError.invoke()
//                    }
//                }
//
//                override fun onFailure(call: Call<GetMoviesResponse>, t: Throwable) {
//                    onError.invoke()
//                }
//            })
//    }

    fun getUpcomingMovies(page: Int = 1) {
        api.getUpcomingMovies(page = page)
            .enqueue(object : Callback<GetMoviesResponse> {
                override fun onResponse(
                        call: Call<GetMoviesResponse>,
                        response: Response<GetMoviesResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
//                            onSuccess.invoke(responseBody.movies)
                            mMoviesList = responseBody.movies
                            mMovies.value = mMoviesList
                        } else {
//                            onError.invoke()
                            Log.d("MoviesRepository: ", "Error")
                        }
                    }
                    else {
//                        onError.invoke()
                        Log.d("MoviesRepository: ", "Error")
                    }
                }

                override fun onFailure(call: Call<GetMoviesResponse>, t: Throwable) {
//                    onError.invoke()
                    Log.d("MoviesRepository: ", "Error")
                }
            })
    }
}