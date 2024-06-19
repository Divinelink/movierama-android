package com.divinelink.core.network.media.model.tv

import com.divinelink.core.commons.extensions.round
import com.divinelink.core.model.media.MediaItem
import com.divinelink.core.network.PaginationData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TvResponseApi(
  val page: Int,
  val results: List<TvItemApi>,
  @SerialName("total_pages") val totalPages: Int,
  @SerialName("total_results") val totalResults: Int
)

@Serializable
data class TvItemApi(
  @SerialName("id") val id: Int,
  @SerialName("adult") val adult: Boolean,
  @SerialName("backdrop_path") val backdropPath: String? = null,
  @SerialName("genre_ids") val genreIds: List<Int>,
  @SerialName("origin_country") val originCountry: List<String>,
  @SerialName("original_language") val originalLanguage: String,
  @SerialName("original_name") val originalName: String,
  @SerialName("overview") val overview: String,
  @SerialName("popularity") val popularity: Double,
  @SerialName("poster_path") val posterPath: String,
  @SerialName("first_air_date") val firstAirDate: String,
  @SerialName("name") val name: String,
  @SerialName("vote_average") val voteAverage: Double,
  @SerialName("vote_count") val voteCount: Int?,
)

fun TvResponseApi.map(): PaginationData<MediaItem.Media> = PaginationData(
  totalPages = totalPages,
  totalResults = totalResults,
  list = this.results.map(TvItemApi::toTv)
)

private fun TvItemApi.toTv() = MediaItem.Media.TV(
  id = this.id,
  posterPath = this.posterPath,
  releaseDate = this.firstAirDate,
  name = this.name,
  rating = this.voteAverage.round(1).toString(),
  overview = this.overview,
  isFavorite = false,
)