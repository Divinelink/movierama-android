package com.divinelink.scenepeek

import androidx.lifecycle.ViewModel
import com.divinelink.core.commons.extensions.extractDetailsFromDeepLink
import com.divinelink.core.model.media.MediaType
import com.divinelink.core.model.person.Gender
import com.divinelink.core.navigation.arguments.DetailsNavArguments
import com.divinelink.core.navigation.arguments.PersonNavArguments
import com.divinelink.scenepeek.ui.ThemedActivityDelegate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel(themedActivityDelegate: ThemedActivityDelegate) :
  ViewModel(),
  ThemedActivityDelegate by themedActivityDelegate {

  private val _uiState: MutableStateFlow<MainUiState> = MutableStateFlow(MainUiState.Completed)
  val uiState: StateFlow<MainUiState> = _uiState

  private val _uiEvent: MutableStateFlow<MainUiEvent> = MutableStateFlow(MainUiEvent.None)
  val uiEvent: StateFlow<MainUiEvent> = _uiEvent

  private fun updateUiEvent(event: MainUiEvent) {
    _uiEvent.value = event
  }

  fun consumeUiEvent() {
    _uiEvent.value = MainUiEvent.None
  }

  fun handleDeepLink(url: String?) {
    val (id, mediaType) = url.extractDetailsFromDeepLink() ?: return

    when (MediaType.from(mediaType)) {
      MediaType.TV, MediaType.MOVIE -> updateUiEvent(
        MainUiEvent.NavigateToDetails(
          DetailsNavArguments(
            id = id,
            mediaType = mediaType,
            isFavorite = false,
          ),
        ),
      )
      MediaType.PERSON -> updateUiEvent(
        MainUiEvent.NavigateToPersonDetails(
          PersonNavArguments(
            id = id.toLong(),
            knownForDepartment = null,
            name = null,
            profilePath = null,
            gender = Gender.NOT_SET,
          ),
        ),
      )
      MediaType.UNKNOWN -> updateUiEvent(MainUiEvent.None)
    }
  }

  /**
   * Activate remote config once Main Activity starts.
   * This is crucial since we can fetch data from remote config and then update our UI
   * once we're ready.
   */
  /*
    init {
      setRemoteConfig()
    }

    fun retryFetchRemoteConfig() {
      setRemoteConfig()
    }

    private fun setRemoteConfig() {
      _uiState.value = MainViewState.Loading
      viewModelScope.launch {
        val result = setRemoteConfigUseCase.invoke(Unit)

        if (result.isSuccess) {
          _uiState.value = MainViewState.Completed
        } else {
          _uiState.value = MainViewState.Error(
            UIText.StringText("Something went wrong. Trying again..."),
          )
        }
      }
    }
   */
}
