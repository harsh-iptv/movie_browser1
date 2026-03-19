package com.example.movie_browser1.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewModelScope
import com.example.movie_browser1.data.repository.MovieRepository
import com.example.movie_browser1.state.MovieUiState
import kotlinx.coroutines.launch

class MovieViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = MovieRepository(application)
    private val _movieUiState = MutableLiveData<MovieUiState>(MovieUiState.Idle)

    val movieUiState: LiveData<MovieUiState> = _movieUiState
    fun fetchMovies() {

        if (_movieUiState.value is MovieUiState.Loading) {
            return
        }

        viewModelScope.launch {
            try {
                // Update UI state to Loading
                _movieUiState.value = MovieUiState.Loading

                // Call repository to get movies (handles cache and API)
                val result = repository.getPopularMovies()

                result.onSuccess { movieResponse ->
                    // Update UI state with fetched movies
                    _movieUiState.value = MovieUiState.Success(movieResponse.results)
                }

                result.onFailure { error ->
                    // Update UI state with error
                    _movieUiState.value = MovieUiState.Error(
                        error.message ?: "Unknown error occurred"
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _movieUiState.value = MovieUiState.Error("Failed to fetch movies: ${e.message}")
            }
        }
    }
    fun refreshMovies() {
        repository.clearCache()
        fetchMovies()
    }


}
