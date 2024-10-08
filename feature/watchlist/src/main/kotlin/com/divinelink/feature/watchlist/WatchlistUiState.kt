package com.divinelink.feature.watchlist

import com.divinelink.core.model.media.MediaItem
import com.divinelink.core.model.media.MediaType

data class WatchlistUiState(
  val selectedTabIndex: Int,
  val tabs: List<WatchlistTab>,
  val pages: Map<MediaType, Int>,
  val forms: Map<MediaType, WatchlistForm<MediaItem.Media>>,
  val canFetchMore: Map<MediaType, Boolean>,
) {
  val mediaType = MediaType.from(WatchlistTab.entries[selectedTabIndex].value)
}
