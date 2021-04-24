package com.example.simplemoviesapp.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.simplemoviesapp.models.Movie
import com.example.simplemoviesapp.R

class MoviesAdapter: RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    private var mMovies: MutableList<Movie> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int = mMovies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(mMovies[position])
    }

    fun appendMovies(movies: List<Movie>) {
        Log.d("MoviesAdapter", "moviesSize: ${movies.size} thisMoviesSize: ${this.mMovies.size}")

        val oldSize = this.mMovies.size
        this.mMovies.addAll(movies)

        Log.d("MoviesAdapter", "moviesSize: ${movies.size} thisMoviesSize: ${this.mMovies.size}")

        notifyItemRangeInserted(
            oldSize,
            movies.size
        )
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val poster: ImageView = itemView.findViewById(R.id.item_movie_poster)
        private val title: TextView = itemView.findViewById(R.id.item_movie_title)
        private val releaseDate: TextView = itemView.findViewById(R.id.item_movie_release_date)
        private val overview: TextView = itemView.findViewById(R.id.item_movie_overview)

        fun bind(movie: Movie) {
            Glide.with(itemView)
                .load("https://image.tmdb.org/t/p/w342${movie.posterPath}")
//                .transform(CenterCrop())
                .into(poster)

            title.text = movie.title
            releaseDate.text = movie.releaseDate
            overview.text = movie.overview
        }
    }
}