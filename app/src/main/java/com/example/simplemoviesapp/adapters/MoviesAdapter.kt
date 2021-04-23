package com.example.simplemoviesapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.simplemoviesapp.R
import com.example.simplemoviesapp.models.Movie

class MoviesAdapter(
    private var movies: MutableList<Movie>
) : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    fun appendMovies(movies: List<Movie>) {
        //Log.d("MoviesAdapter", "moviesSize: ${movies.size} thisMoviesSize: ${this.movies.size}")

        val oldSize = this.movies.size
        this.movies.addAll(movies)

        //Log.d("MoviesAdapter", "moviesSize: ${movies.size} thisMoviesSize: ${this.movies.size}")

        notifyItemRangeInserted(
            oldSize,
            movies.size
        )
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val poster: ImageView = itemView.findViewById(R.id.item_movie_poster)

        fun bind(movie: Movie) {

        }
    }
}