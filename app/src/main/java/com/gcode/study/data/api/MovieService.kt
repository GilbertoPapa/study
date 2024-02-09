package com.gcode.study.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface MovieService {

    @GET("movie/popular")
    @Headers("accept: application/json")
    suspend fun getMovies(): Response<MovieResponse>
}