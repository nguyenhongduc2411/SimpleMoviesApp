package com.example.simplemoviesapp.utils

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.simplemoviesapp.R

//https://github.com/mitchtabian/DataBindingGettingStarted
@BindingAdapter("posterResource")
fun setPosterResource(poster: ImageView, poster_path: String?) {
    val url: String = "https://image.tmdb.org/t/p/w342$poster_path"

    val context = poster.context

    val options = RequestOptions()
//        .placeholder(R.drawable.ic_launcher_background)
        .error(R.drawable.ic_launcher_background)

    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(url)
        .into(poster)
}