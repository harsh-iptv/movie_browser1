package com.example.movie_browser1.data.api
import com.example.movie_browser1.data.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

// Retrofit interface for JSON Fakery API
// API Documentation: https://jsonfakery.com/movies-list
// No API key required - free to use!
interface MovieApiService {

    @GET("movies/paginated")
    suspend fun getPopularMovies(
        @Query("page") page: Int = 1
    ): MovieResponse
}
