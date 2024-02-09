package com.gcode.study.domain

import android.util.Log
import com.gcode.study.data.repository.RepositoryState
import com.gcode.study.data.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MovieUseCase(private val movieRepository: MovieRepository) {

    suspend fun getMovies(): Flow<MovieState> = flow {
        runCatching {
            movieRepository.getMovies()
        }.onSuccess { result ->
            when (result) {
                is RepositoryState.Success -> {
                    val movies = MoviesMapper.mapToDto(result.movies)
                    emit(MovieState.SuccessState(movies))
                }

                is RepositoryState.ClientError -> {
                    emit(MovieState.RetryState(result.message))
                }

                is RepositoryState.ServerError -> {
                    emit(MovieState.ErrorState(result.message))
                }

                is RepositoryState.Empty -> {
                    emit(MovieState.ErrorState("Lista vazia"))
                }

                is RepositoryState.UnknownError -> {
                    emit(MovieState.ErrorState(result.message))
                }
            }
        }.onFailure {
            Log.d("DOMAIN", "${it.cause}:${it.message}")
            emit(MovieState.ExceptionState)
        }
    }

}