package com.divinelink.core.data.account

import com.divinelink.core.model.media.MediaItem
import com.divinelink.core.network.PaginationData
import kotlinx.coroutines.flow.Flow

interface AccountRepository {

  suspend fun fetchMoviesWatchlist(
    page: Int,
    sortBy: String,
    accountId: String,
  ): Flow<Result<PaginationData<MediaItem.Media>>>

  suspend fun fetchTvShowsWatchlist(
    page: Int,
    sortBy: String,
    accountId: String,
  ): Flow<Result<PaginationData<MediaItem.Media>>>
}
