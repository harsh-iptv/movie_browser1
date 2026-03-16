package com.example.movie_browser1.data.model
import android.graphics.Movie
import com.google.gson.annotations.SerializedName
data class movie (
    @SerializedName("Id")
    val id: String,
    @SerializedName("Title")
    val title: String,
    @SerializedName("Overview")
    val overview: String,
    @SerializedName("Poster_path")
    val posterPath: String?,
    @SerializedName("Release_date")
    val releaseDate: String,
    @SerializedName("vote_count")
    val voteCount: Int

)
data class MovieResponse(
    @SerializedName("data")
    val results: List<Movie>,

    @SerializedName("last_page")
    val totalPages: Int,

    @SerializedName("current_page")
    val page: Int
)