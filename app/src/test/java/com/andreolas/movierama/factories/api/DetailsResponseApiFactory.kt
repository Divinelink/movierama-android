package com.andreolas.movierama.factories.api

import com.andreolas.movierama.base.data.remote.movies.dto.details.DetailsResponseApi
import com.andreolas.movierama.factories.CreditsFactory
import com.andreolas.movierama.factories.GenreFactory

object DetailsResponseApiFactory {

  fun Movie() = DetailsResponseApi.Movie(
    adult = false,
    backdropPath = "",
    belongToCollection = null,
    budget = 0,
    genres = GenreFactory.all(),
    homepage = null,
    id = 1123,
    imdbId = null,
    originalLanguage = "",
    originalTitle = "",
    overview = "This movie is good.",
    popularity = 0.0,
    posterPath = "123456",
    productionCompanies = listOf(),
    productionCountries = listOf(),
    releaseDate = "2022",
    revenue = 0,
    runtime = 130,
    spokenLanguage = listOf(),
    status = null,
    tagline = "",
    title = "Flight Club",
    video = false,
    voteAverage = 9.5,
    voteCount = 0,
    credits = CreditsFactory.all()
  )

}
