package com.example.movie_browser1.state

import com.example.movie_browser1.data.model.Movie

sealed class MovieUiState {
    object Loading: MovieUiState()
    object Idle: MovieUiState()
    data class Success(val movies: List<Movie>): MovieUiState()
    data class Error(val message: String): MovieUiState()
}
