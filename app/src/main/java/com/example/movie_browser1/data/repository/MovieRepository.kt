package com.example.movie_browser1.data.repository

import android.content.Context
import com.example.movie_browser1.data.api.RetrofitClient
import com.example.movie_browser1.data.model.MovieResponse

class MovieRepository(context: Context) {

    private val apiService = RetrofitClient.movieApiService

    suspend fun getPopularMovies(): Result<MovieResponse> {
        return try {
            val response = apiService.getPopularMovies()
            Result.success(response)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
    fun clearCache() {
        // empty - cache not implemented yet
    }


}
