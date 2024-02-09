package com.gcode.study.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gcode.study.core.Utils.isUpToThree
import com.gcode.study.domain.MovieState
import com.gcode.study.domain.MovieUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MovieViewModel(private val useCase: MovieUseCase) : ViewModel() {

    private var totalRetry: Int = 0

    private val _state = MutableStateFlow<MovieMicroState>(MovieMicroState.ShowLoading)
    val state: StateFlow<MovieMicroState> = _state

    fun onIntent(intent: MovieIntent) {
        when (intent) {
            is MovieIntent.LoadMovies -> {
                execute()
            }

            is MovieIntent.LoadRetry -> {
                totalRetry++
                retryListMovies()
            }

            is MovieIntent.LoadNewFlow -> {
                _state.value = MovieMicroState.ShowNewFlow
            }

            is MovieIntent.RefreshMovies -> {
                execute()
            }
        }
    }

    private fun execute() {
        runCatching {
            getListMovies()
        }.onFailure {
            _state.value = MovieMicroState.ShowError(it.message ?: "Erro desconhecido")
        }
    }


    private fun retryListMovies() {
        totalRetry.isUpToThree {
            _state.value = MovieMicroState.ShowLoading
            execute()
        } ?: run {
            _state.value = MovieMicroState.ShowError("Erro ao carregar filmes")
        }
    }

    private fun getListMovies() {
        viewModelScope.launch {
            useCase.getMovies().collect {
                when (it) {
                    is MovieState.SuccessState -> {
                        _state.value = MovieMicroState.ShowMovies(it.movies)
                    }

                    is MovieState.ErrorState -> {
                        _state.value = MovieMicroState.ShowError(it.message)
                        Log.d("TAG", "getListMovies: ${it.message}")
                    }

                    is MovieState.RetryState -> {
                        _state.value = MovieMicroState.ShowRetry(it.message)
                        Log.d("TAG", "getListMovies: ${it.message}")
                    }

                    is MovieState.ExceptionState -> {
                        _state.value = MovieMicroState.ShowError("exception")
                    }
                }
            }
        }
    }
}