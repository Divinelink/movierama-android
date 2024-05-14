package com.divinelink.core.network.movies.model.states

import kotlinx.serialization.Serializable

@Serializable
data class AccountMediaDetailsResponseApi(
  val id: Int,
  val favorite: Boolean,
  val rated: RateResponseApi,
  val watchlist: Boolean,
)