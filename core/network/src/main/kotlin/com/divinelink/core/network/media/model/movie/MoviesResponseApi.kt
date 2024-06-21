package com.divinelink.core.network.media.model.movie

import com.divinelink.core.commons.extensions.round
import com.divinelink.core.model.media.MediaItem
import com.divinelink.core.network.PaginationData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MoviesResponseApi(
  val page: Int,
  val results: List<MovieApi>,
  @SerialName("total_pages") val totalPages: Int,
  @SerialName("total_results") val totalResults: Int,
)

@Serializable
data class MovieApi(
  val adult: Boolean,
  @SerialName("backdrop_path") val backdropPath: String?,
  @SerialName("genre_ids") val genreIds: List<Int>,
  val id: Int,
  @SerialName("original_language") val originalLanguage: String,
  @SerialName("original_title") val originalTitle: String,
  val overview: String,
  val popularity: Double,
  @SerialName("poster_path") val posterPath: String?,
  @SerialName("release_date") val releaseDate: String,
  val title: String,
  val video: Boolean,
  @SerialName("vote_average") val voteAverage: Double,
  @SerialName("vote_count") val voteCount: Int?,
)

fun MoviesResponseApi.map(): PaginationData<MediaItem.Media> = PaginationData(
  totalPages = totalPages,
  totalResults = totalResults,
  list = this.results.map(MovieApi::toMovie),
)

fun MoviesResponseApi.toMoviesList(): List<MediaItem.Media.Movie> =
  this.results.map(MovieApi::toMovie)

private fun MovieApi.toMovie() = MediaItem.Media.Movie(
  id = this.id,
  posterPath = this.posterPath ?: "",
  releaseDate = this.releaseDate,
  name = this.title,
  rating = this.voteAverage.round(1).toString(),
  overview = this.overview,
  isFavorite = false,
)
