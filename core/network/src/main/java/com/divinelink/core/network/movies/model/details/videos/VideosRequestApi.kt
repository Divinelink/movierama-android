package com.divinelink.core.network.movies.model.details.videos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VideosRequestApi(
  @SerialName("movie_id")
  val movieId: Int,
)