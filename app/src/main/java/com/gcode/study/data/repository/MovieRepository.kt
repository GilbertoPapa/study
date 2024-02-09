package com.gcode.study.data.repository

import android.util.Log
import com.gcode.study.data.api.MovieService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRepository(private val movieService: MovieService) {

    suspend fun getMovies(): RepositoryState {
        return withContext(Dispatchers.IO) {

            val response = movieService.getMovies()

            when {
                response.isSuccessful -> {
                    Log.d("NETWORK", "${response.code()} : ${response.headers()["date"]}")
                    response.body()?.result.let { result ->
                        if (result.isNullOrEmpty()) {
                            RepositoryState.Empty
                        } else {
                            RepositoryState.Success(result)
                        }
                    }
                }

                response.code() in 400..499 -> {
                    Log.d(
                        "NETWORK",
                        "${response.code()} : ${response.message()} : ${response.errorBody()}"
                    )
                    RepositoryState.ClientError("algo deu errado")
                }

                response.code() in 500..599 -> {
                    Log.d(
                        "NETWORK",
                        "${response.code()} : ${response.message()} : ${response.errorBody()}"
                    )
                    RepositoryState.ServerError("servidor indisponivel")
                }

                else -> {
                    Log.d(
                        "NETWORK",
                        "${response.code()} : ${response.message()} : ${response.errorBody()}"
                    )
                    RepositoryState.UnknownError("indisponivel no momento")
                }
            }
        }
    }
}