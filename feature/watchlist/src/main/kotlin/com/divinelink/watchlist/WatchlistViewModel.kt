package com.divinelink.watchlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.divinelink.core.domain.FetchWatchlistUseCase
import com.divinelink.core.domain.WatchlistParameters
import com.divinelink.core.domain.WatchlistResponse
import com.divinelink.core.model.media.MediaType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class WatchlistViewModel @Inject constructor(
  private val fetchWatchlistUseCase: FetchWatchlistUseCase,
) : ViewModel() {

  private val _uiState: MutableStateFlow<WatchlistUiState> = MutableStateFlow(
    WatchlistUiState(
      selectedTabIndex = WatchlistTab.MOVIE.ordinal,
      tabs = WatchlistTab.entries,
      pages = mapOf(
        MediaType.MOVIE to 1,
        MediaType.TV to 1
      ),
      forms = mapOf(
        MediaType.MOVIE to WatchlistForm.Loading,
        MediaType.TV to WatchlistForm.Loading
      ),
      canFetchMore = mapOf(
        MediaType.MOVIE to true,
        MediaType.TV to true
      )
    )
  )
  val uiState: StateFlow<WatchlistUiState> = _uiState

  init {
    fetchWatchlist(mediaType = MediaType.MOVIE)
  }

  fun onLoadMore() {
    val uiState = _uiState.value
    val mediaType = uiState.mediaType
    val currentPage = uiState.pages[mediaType] ?: 1
    val canFetchMore = uiState.canFetchMore[mediaType] ?: false

    if (canFetchMore) {
      fetchWatchlist(
        mediaType = mediaType,
        page = currentPage
      )
    }
  }

  fun onTabSelected(tab: Int) {
    _uiState.update { uiState ->
      uiState.copy(selectedTabIndex = tab)
    }

    if (_uiState.value.tvFormIsLoading) {
      fetchWatchlist(mediaType = MediaType.TV)
    }
  }

  private fun fetchWatchlist(
    mediaType: MediaType,
    page: Int = 1
  ) {
    viewModelScope.launch {
      fetchWatchlistUseCase.invoke(
        parameters = WatchlistParameters(
          page = page,
          mediaType = mediaType
        )
      ).collectLatest { result ->
        result.onSuccess { response ->
          updateUiState(response)
        }.onFailure {

        }
      }
    }
  }

  private fun updateUiState(
    response: WatchlistResponse
  ) {
    _uiState.update { uiState ->
      val currentData = (uiState.forms[response.type] as? WatchlistForm.Data)?.data.orEmpty()
      val currentPage = uiState.pages[response.type] ?: 1

      uiState.copy(
        forms = uiState.forms + (response.type to WatchlistForm.Data(currentData + response.data)),
        pages = uiState.pages + (response.type to currentPage + 1),
        canFetchMore = uiState.canFetchMore + (response.type to response.canFetchMore)
      ).run {
        Timber.d("Updating Ui for ${response.type} with data ${response.data}")
        Timber.d("Update page for ${response.type} to ${currentPage + 1}")
        Timber.d("Can fetch more for ${response.type} is ${response.canFetchMore}")
        this
      }
    }
  }
}
