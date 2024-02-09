package com.gcode.study.data.repository

import com.gcode.study.data.api.Movie

sealed class RepositoryState {
    data class Success(val movies: List<Movie>) : RepositoryState()
    data class ClientError(val message: String) : RepositoryState()
    data class ServerError(val message: String) : RepositoryState()
    data class UnknownError(val message: String) : RepositoryState()
    object Empty : RepositoryState()
}
