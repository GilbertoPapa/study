package com.gcode.study.presentation

import com.gcode.study.domain.MovieDTO

sealed class MovieMicroState {
    data class ShowError(val message: String) : MovieMicroState()
    data class ShowRetry(val message: String) : MovieMicroState()
    data class ShowMovies(val list: List<MovieDTO>) : MovieMicroState()
    object ShowLoading : MovieMicroState()
    object ShowNewFlow : MovieMicroState()
}