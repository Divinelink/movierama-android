package com.andreolas.movierama.main.ui

import com.andreolas.movierama.MainUiEvent
import com.andreolas.movierama.MainUiState
import com.andreolas.movierama.MainViewModel
import com.andreolas.movierama.fakes.usecase.FakeSetRemoteConfigUseCase
import com.andreolas.movierama.test.util.fakes.FakeThemedActivityDelegate
import com.divinelink.core.testing.MainDispatcherRule
import com.google.common.truth.Truth.assertThat
import org.junit.Rule

class MainViewModelRobot {
  private lateinit var viewModel: MainViewModel

  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()

  private val fakeSetRemoteConfigUseCase = FakeSetRemoteConfigUseCase()
  private val fakeThemedActivityDelegate = FakeThemedActivityDelegate()

  fun buildViewModel() = apply {
    viewModel = MainViewModel(
      themedActivityDelegate = fakeThemedActivityDelegate,
    )
  }

  fun onHandleDeeplink(uri: String?) = apply {
    viewModel.handleDeepLink(uri)
  }

  fun onConsumeUiEvent() = apply {
    viewModel.consumeUiEvent()
  }

  fun assertUiState(expectedUiState: MainUiState) = apply {
    assertThat(viewModel.uiState.value).isEqualTo(expectedUiState)
  }

  fun assertUiEvent(expectedUiEvent: MainUiEvent) = apply {
    assertThat(viewModel.uiEvent.value).isEqualTo(expectedUiEvent)
  }

  fun mockSetRemoteConfigResult(result: Unit) = apply {
    fakeSetRemoteConfigUseCase.mockSetRemoteConfigResult(result)
  }
}
