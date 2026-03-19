package com.example.movie_browser1.data.model

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("id")
    val id: String,

    @SerializedName("movie_id")
    val movieId: Int,

    @SerializedName("original_title")
    val title: String,

    @SerializedName("original_language")
    val language: String,

    @SerializedName("overview")
    val overview: String,

    @SerializedName("popularity")
    val popularity: Double,

    @SerializedName("poster_path")
    val posterPath: String?,

    @SerializedName("backdrop_path")
    val backdropPath: String?,

    @SerializedName("release_date")
    val releaseDate: String,

    @SerializedName("vote_average")
    val voteAverage: Double,

    @SerializedName("vote_count")
    val voteCount: Int,

    @SerializedName("adult")
    val adult: Int,

    @SerializedName("casts")
    val casts: List<Cast>
)

data class Cast(
    @SerializedName("id")
    val id: String,

    @SerializedName("movie_id")
    val movieId: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("original_name")
    val originalName: String,

    @SerializedName("popularity")
    val popularity: String,

    @SerializedName("profile_path")
    val profilePath: String?,

    @SerializedName("character")
    val character: String
)

data class MovieResponse(
    @SerializedName("data")
    val results: List<Movie>,

    @SerializedName("last_page")
    val totalPages: Int,

    @SerializedName("current_page")
    val page: Int,

    @SerializedName("total")
    val total: Int
)