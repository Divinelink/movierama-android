package com.andreolas.movierama.base.data.remote.movies.dto.search.multi

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MultiSearchResponseApi(
  @SerialName("page") val page: Int,
  @SerialName("results") val results: List<MultiSearchResultApi>,
  @SerialName("total_pages") val totalPages: Int,
  @SerialName("total_results") val totalResults: Int,
)
