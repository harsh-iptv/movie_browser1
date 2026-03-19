package com.example.movie_browser1

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movie_browser1.databinding.ActivityMainBinding
import com.example.movie_browser1.ui.adapter.MovieAdapter
import com.example.movie_browser1.state.MovieUiState
import com.example.movie_browser1.ui.viewmodel.MovieViewModel

class MainActivity : AppCompatActivity() {

    // ViewBinding - type safe access to all views in activity_main.xml
    private lateinit var binding: ActivityMainBinding

    // ViewModel - survives screen rotation, holds UI state
    private val viewModel: MovieViewModel by viewModels()

    // Adapter - connects movie data to RecyclerView
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Step 1 - inflate the layout using ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Step 2 - handle edge to edge display (status bar, nav bar)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Step 3 - setup everything
        setupRecyclerView()
        setupClickListeners()
        observeUiState()

        // Step 4 - trigger first data fetch
        viewModel.fetchMovies()
    }

    // Setup RecyclerView with adapter and layout manager
    private fun setupRecyclerView() {
        movieAdapter = MovieAdapter()
        binding.movieRecyclerView.apply {
            // LinearLayoutManager = vertical scrolling list
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = movieAdapter
        }
    }

    // Setup button click listeners
    private fun setupClickListeners() {

        // Refresh - clears cache (when added) and fetches fresh data
        binding.refreshButton.setOnClickListener {
            viewModel.refreshMovies()
        }

        // Retry - tries fetching again after an error
        binding.retryButton.setOnClickListener {
            viewModel.fetchMovies()
        }
    }

    // Observe ViewModel's LiveData - reacts to every state change
    private fun observeUiState() {
        viewModel.movieUiState.observe(this) { uiState ->
            when (uiState) {

                // App just opened - hide everything
                is MovieUiState.Idle -> hideAll()

                // Fetching data - show spinner, hide everything else
                is MovieUiState.Loading -> {
                    binding.loadingContainer.visibility = View.VISIBLE
                    binding.errorContainer.visibility   = View.GONE
                    binding.movieRecyclerView.visibility = View.GONE
                }

                // Data loaded - show list, hide everything else
                is MovieUiState.Success -> {
                    binding.loadingContainer.visibility  = View.GONE
                    binding.errorContainer.visibility    = View.GONE
                    binding.movieRecyclerView.visibility = View.VISIBLE

                    // Pass movies to adapter - triggers RecyclerView to draw
                    movieAdapter.updateMovies(uiState.movies)
                }

                // Something went wrong - show error message + retry button
                is MovieUiState.Error -> {
                    binding.loadingContainer.visibility  = View.GONE
                    binding.errorContainer.visibility    = View.VISIBLE
                    binding.movieRecyclerView.visibility = View.GONE

                    // Show the actual error message from ViewModel
                    binding.errorText.text = uiState.message
                }

                is MovieUiState.Error -> TODO()
                MovieUiState.Idle -> TODO()
                MovieUiState.Loading -> TODO()
                is MovieUiState.Success -> TODO()
            }
        }
    }

    // Helper - hides all three state containers at once
    private fun hideAll() {
        binding.loadingContainer.visibility  = android.view.View.GONE
        binding.errorContainer.visibility    = android.view.View.GONE
        binding.movieRecyclerView.visibility = android.view.View.GONE
    }
}
