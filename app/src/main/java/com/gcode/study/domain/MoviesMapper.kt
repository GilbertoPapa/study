package com.gcode.study.domain

import com.gcode.study.data.api.Movie

object MoviesMapper {
    fun mapToDto(list: List<Movie>) = list.map { movie ->
        MovieDTO(
            id = movie.id,
            title = movie.title,
            overview = movie.overview,
            posterPath = movie.posterPath,
            releaseDate = movie.releaseDate,
            voteAverage = movie.voteAverage,
            voteCount = movie.voteCount,
            popularity = movie.popularity,
            originalLanguage = movie.originalLanguage,
            originalTitle = movie.originalTitle,
            backdropPath = movie.backdropPath,
            adult = movie.adult,
            video = movie.video,
            genreIds = movie.genreIds
        )
    }
}