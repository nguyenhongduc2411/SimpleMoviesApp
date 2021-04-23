package com.example.simplemoviesapp.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop

@BindingAdapter("image")
fun loadImage(image: ImageView, poster_path: String){
    val url: String = "http://image.tmdb.org/t/p/w342$poster_path"
    Glide.with(image).load(url).transform(CenterCrop()).into(image)
}