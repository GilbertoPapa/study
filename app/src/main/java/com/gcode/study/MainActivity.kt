package com.gcode.study

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gcode.study.presentation.component.MoviesScreen
import com.gcode.study.di.MovieProviders
import com.gcode.study.presentation.MovieIntent
import com.gcode.study.presentation.MovieViewModel
import com.gcode.study.ui.theme.StudyTheme

class MainActivity : ComponentActivity() {

    private val viewModel: MovieViewModel by lazy {
        MovieProviders.providerMovieViewModel()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupContent()
    }

    private fun setupContent() {
        setContent {
            StudyTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    MoviesScreen(viewModel)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        setupMovies()
    }

    private fun setupMovies() {
        viewModel.onIntent(MovieIntent.LoadMovies)
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview(viewModel : MovieViewModel = viewModel()) {
    StudyTheme {

        Surface () {
            MoviesScreen(viewModel)

        }
    }
}