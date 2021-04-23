package com.example.simplemoviesapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String = "ffafbd7ad7beaef52e88f04b016c6a61",
        @Query("page") page: Int
    ): Call<GetMoviesResponse>
}