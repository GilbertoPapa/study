package com.gcode.study.domain

sealed class MovieState {
    data class SuccessState(val movies: List<MovieDTO>) : MovieState()
    data class ErrorState(val message: String) : MovieState()
    data class RetryState(val message: String) : MovieState()
    object ExceptionState : MovieState()
}