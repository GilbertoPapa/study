package com.gcode.study.presentation

sealed class MovieIntent{
    object LoadMovies : MovieIntent()
    object RefreshMovies : MovieIntent()
    object LoadNewFlow : MovieIntent()
    object LoadRetry : MovieIntent()
}
