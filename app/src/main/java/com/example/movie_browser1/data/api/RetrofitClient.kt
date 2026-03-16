package com.example.movie_browser1.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = "https://jsonfakery.com/"

    val movieApiService: MovieApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)              // WHERE to send requests
            .addConverterFactory(GsonConverterFactory.create())  // HOW to convert JSON
            .build()
            .create(MovieApiService::class.java)  // WHO handles it
    }
}