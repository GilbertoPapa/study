package com.gcode.study.di

import com.gcode.study.data.repository.MovieRepository
import com.gcode.study.data.api.MovieService
import com.gcode.study.domain.MovieUseCase
import com.gcode.study.presentation.MovieViewModel

object MovieProviders {

    private val api: MovieService by lazy {
        RetrofitConfig.instanceRetrofit.create(MovieService::class.java)
    }

    private val repository by lazy {
        MovieRepository(api)
    }

    private val useCase by lazy {
        MovieUseCase(repository)
    }

    fun providerMovieViewModel(): MovieViewModel {
        return MovieViewModel(useCase)
    }

}