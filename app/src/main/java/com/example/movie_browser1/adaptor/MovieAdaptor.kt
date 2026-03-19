package com.example.movie_browser1.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movie_browser1.data.model.Movie
import com.example.movie_browser1.databinding.ItemMovieBinding

// RecyclerView.Adapter needs a ViewHolder type — we pass our own MovieViewHolder
// MutableList starts empty, movies are added later via updateMovies()
class MovieAdapter(private val movies: MutableList<Movie> = mutableListOf()) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    // ─── VIEWHOLDER ──────────────────────────────────────────────
    // inner class means it can access the outer adapter's properties
    // binding gives us direct access to all views in item_movie.xml
    inner class MovieViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        // bind() is called every time this ViewHolder is recycled
        // it fills the views with the current movie's data
        fun bind(movie: Movie) {
            binding.apply {

                // movie.title maps to original_title from API
                movieTitleText.text = movie.title

                // release date straight from API e.g. "Fri, 10/07/1994"
                movieReleaseDateText.text = "Released: ${movie.releaseDate}"

                // format to 1 decimal place e.g. 8.4/10
                movieRatingText.text = "Rating: ${String.format("%.1f", movie.voteAverage)}/10"

                // overview text, capped at 3 lines in XML
                movieOverviewText.text = movie.overview

                // show how many people voted e.g. "1,234 votes"
                movieVoteCountText.text = "${movie.voteCount} votes"

                // show language e.g. "Language: en"
                movieLanguageText.text = "Language: ${movie.language}"
            }
        }
    }

    // ─── ONCREATEVIEWHOLDER ──────────────────────────────────────
    // called only when a NEW ViewHolder needs to be created (~10 times)
    // inflates item_movie.xml and wraps it in a ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(
            LayoutInflater.from(parent.context),  // knows how to inflate XML
            parent,                                // the RecyclerView itself
            false                                  // don't attach to parent yet
        )
        return MovieViewHolder(binding)
    }

    // ─── ONBINDVIEWHOLDER ────────────────────────────────────────
    // called every time an item scrolls into view
    // takes a recycled ViewHolder and fills it with new data
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])  // position = index in the list
    }

    // ─── GETITEMCOUNT ────────────────────────────────────────────
    // RecyclerView asks this to know when to stop creating items
    override fun getItemCount(): Int = movies.size

    // ─── UPDATEMOVIES ────────────────────────────────────────────
    // called from MainActivity when ViewModel posts Success state
    // clears old list, adds new movies, tells RecyclerView to redraw
    fun updateMovies(newMovies: List<Movie>) {
        movies.clear()           // remove old data
        movies.addAll(newMovies) // add new data
        notifyDataSetChanged()   // tell RecyclerView to refresh UI
    }

}