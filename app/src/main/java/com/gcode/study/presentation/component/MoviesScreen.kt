package com.gcode.study.presentation.component

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gcode.study.presentation.MovieIntent
import com.gcode.study.presentation.MovieMicroState
import com.gcode.study.presentation.MovieViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun MoviesScreen(viewModel: MovieViewModel = viewModel()) {
    val listMoviesState by viewModel.state.collectAsState()

    var isRefreshing by remember { mutableStateOf(false) }

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
        content = {

            MovieContent(
                listMoviesState = listMoviesState,
                viewModel = viewModel,
                setIsRefreshing = { isRefreshing = it })
        },
        onRefresh = {
            isRefreshing = true
            viewModel.onIntent(MovieIntent.RefreshMovies)
        })
}

@Composable
private fun MovieContent(
    listMoviesState: MovieMicroState,
    viewModel: MovieViewModel,
    setIsRefreshing: (Boolean) -> Unit
) {
    listMoviesState.let { state ->

        when (state) {
            is MovieMicroState.ShowLoading -> {
                LoadingScreen()
            }

            is MovieMicroState.ShowMovies -> {
                val movies = state.list
                LazyColumn {
                    items(movies.size) { movie ->
                        MovieCard(
                            title = movies[movie].title,
                            subTitle = movies[movie].overview
                        )
                    }
                }
                setIsRefreshing(false)
            }

            is MovieMicroState.ShowError -> {
                ErrorScreen(
                    message = state.message,
                    titleButton = "Seguir para outro produto",
                    onClick = {
                        viewModel.onIntent(MovieIntent.LoadNewFlow)
                    })
            }

            is MovieMicroState.ShowRetry -> {
                ErrorScreen(message = state.message, titleButton = "Try again", onClick = {
                    viewModel.onIntent(MovieIntent.LoadRetry)
                })
            }

            is MovieMicroState.ShowNewFlow -> {
                OthersFeatures(title = "New Flow")
            }
        }
    }
}