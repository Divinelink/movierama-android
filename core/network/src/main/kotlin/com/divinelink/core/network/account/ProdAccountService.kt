package com.divinelink.core.network.account

import com.divinelink.core.network.client.RestClient
import com.divinelink.core.network.media.model.movie.MoviesResponseApi
import com.divinelink.core.network.media.model.search.multi.MultiSearchResponseApi
import com.divinelink.core.network.media.model.tv.TvResponseApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProdAccountService @Inject constructor(
  private val restClient: RestClient,
) : AccountService {

  override fun fetchMoviesWatchlist(
    page: Int,
    sortBy: String,
    accountId: String,
  ): Flow<MoviesResponseApi> = flow {
    val url = "${restClient.tmdbUrl}/account/$accountId/watchlist/movies" +
      "?page=$page" +
      "&sort_by=created_at.$sortBy"

    val response = restClient.get<MoviesResponseApi>(url = url)

    emit(response)
  }

  override fun fetchTvShowsWatchlist(
    page: Int,
    sortBy: String,
    accountId: String,
  ): Flow<TvResponseApi> = flow {
    val url = "${restClient.tmdbUrl}/account/$accountId/watchlist/tv" +
      "?page=$page" +
      "&sort_by=created_at.$sortBy"

    val response = restClient.get<TvResponseApi>(url = url)

    emit(response)
  }
}
