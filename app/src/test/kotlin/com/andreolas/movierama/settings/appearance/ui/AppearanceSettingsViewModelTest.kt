package com.andreolas.movierama.settings.appearance.ui

import com.andreolas.movierama.MainDispatcherRule
import com.andreolas.movierama.settings.app.appearance.AppearanceSettingsViewModel
import com.andreolas.movierama.settings.app.appearance.UpdateSettingsState
import com.andreolas.movierama.settings.app.appearance.usecase.GetAvailableThemesUseCase
import com.andreolas.movierama.settings.app.appearance.usecase.GetThemeUseCase
import com.andreolas.movierama.settings.app.appearance.usecase.SetThemeUseCase
import com.andreolas.movierama.settings.app.appearance.usecase.black.backgrounds.GetBlackBackgroundsUseCase
import com.andreolas.movierama.settings.app.appearance.usecase.black.backgrounds.SetBlackBackgroundsUseCase
import com.andreolas.movierama.settings.app.appearance.usecase.material.you.GetMaterialYouUseCase
import com.andreolas.movierama.settings.app.appearance.usecase.material.you.SetMaterialYouUseCase
import com.andreolas.movierama.settings.appearance.usecase.material.you.FakeGetMaterialYouVisibleUseCase
import com.andreolas.movierama.test.util.fakes.FakePreferenceStorage
import com.divinelink.core.designsystem.theme.Theme
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AppearanceSettingsViewModelTest {

  @OptIn(ExperimentalCoroutinesApi::class)
  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()
  @OptIn(ExperimentalCoroutinesApi::class)
  val testDispatcher = mainDispatcherRule.testDispatcher

  private lateinit var viewModel: AppearanceSettingsViewModel

  private val fakeGetMaterialYouVisibleUseCase = FakeGetMaterialYouVisibleUseCase(testDispatcher)

  @Before
  fun setup() {
    fakeGetMaterialYouVisibleUseCase.mockMaterialYouVisible(false)
  }

  private fun buildViewModel(fakePreferenceStorage: FakePreferenceStorage) = apply {
    viewModel = AppearanceSettingsViewModel(
      setThemeUseCase = SetThemeUseCase(fakePreferenceStorage, testDispatcher),
      getThemeUseCase = GetThemeUseCase(fakePreferenceStorage, testDispatcher),
      getAvailableThemesUseCase = GetAvailableThemesUseCase(testDispatcher),
      setMaterialYouUseCase = SetMaterialYouUseCase(fakePreferenceStorage, testDispatcher),
      getMaterialYouUseCase = GetMaterialYouUseCase(fakePreferenceStorage, testDispatcher),
      setBlackBackgroundsUseCase = SetBlackBackgroundsUseCase(
        preferenceStorage = fakePreferenceStorage,
        dispatcher = testDispatcher
      ),
      getBlackBackgroundsUseCase = GetBlackBackgroundsUseCase(
        preferenceStorage = fakePreferenceStorage,
        dispatcher = testDispatcher
      ),
      getMaterialYouVisibleUseCase = fakeGetMaterialYouVisibleUseCase
    )
  }

  @Test
  fun `given theme is system, when I set theme to dark, then I expect dark theme`() = runTest {
    // Given
    buildViewModel(FakePreferenceStorage(selectedTheme = Theme.SYSTEM.storageKey))
    // When
    viewModel.setTheme(Theme.DARK)
    // Then
    val state = viewModel.uiState.first()
    state.assertState(theme = Theme.DARK)
  }

  @Test
  fun `given theme is system, then I expect system theme`() = runTest {
    // Given
    buildViewModel(FakePreferenceStorage(selectedTheme = Theme.SYSTEM.storageKey))
    // Then
    val state = viewModel.uiState.first()
    state.assertState(theme = Theme.SYSTEM)
  }

  @Test
  fun `given theme is dark, then I expect dark theme`() = runTest {
    // Given
    buildViewModel(FakePreferenceStorage(selectedTheme = Theme.DARK.storageKey))
    // Then
    val state = viewModel.uiState.first()
    state.assertState(theme = Theme.DARK)
  }

  @Test
  fun `given theme is light, then I expect light theme`() = runTest {
    // Given
    buildViewModel(FakePreferenceStorage(selectedTheme = Theme.LIGHT.storageKey))
    // Then
    val state = viewModel.uiState.first()
    state.assertState(theme = Theme.LIGHT)
  }

  @Test
  fun `given material you is enabled, when I set it to false, then I expect false`() = runTest {
    // Given
    buildViewModel(FakePreferenceStorage(isMaterialYouEnabled = true))
    // When
    viewModel.setMaterialYou(false)
    // Then
    val state = viewModel.uiState.first()
    state.assertState(materialYouEnabled = false)
  }

  @Test
  fun `given material you is disabled, when I set it to true, then I expect true`() = runTest {
    // Given
    buildViewModel(FakePreferenceStorage(isMaterialYouEnabled = false))
    // When
    viewModel.setMaterialYou(true)
    // Then
    val state = viewModel.uiState.first()
    state.assertState(materialYouEnabled = true)
  }

  @Test
  fun `given black backgrounds is enabled, when I disable it, then I expect false`() = runTest {
    // Given
    buildViewModel(FakePreferenceStorage(isBlackBackgroundsEnabled = true))
    // When
    viewModel.setBlackBackgrounds(false)
    // Then
    val state = viewModel.uiState.first()
    state.assertState(blackBackgroundsEnabled = false)
  }

  @Test
  fun `given black backgrounds is disabled, when I enable it, then I expect true`() = runTest {
    // Given
    buildViewModel(FakePreferenceStorage(isBlackBackgroundsEnabled = false))
    // When
    viewModel.setBlackBackgrounds(true)
    // Then
    val state = viewModel.uiState.first()
    state.assertState(blackBackgroundsEnabled = true)
  }

  @Test
  fun `given material you is visible, then I expect material you visible`() = runTest {
    // Given
    fakeGetMaterialYouVisibleUseCase.mockMaterialYouVisible(true)
    buildViewModel(FakePreferenceStorage())
    // Then
    val state = viewModel.uiState.first()
    state.assertState(materialYouVisible = true)
  }

  private fun UpdateSettingsState.assertState(
    theme: Theme = Theme.SYSTEM,
    availableThemes: List<Theme> = listOf(Theme.LIGHT, Theme.DARK),
    materialYouEnabled: Boolean = false,
    materialYouVisible: Boolean = false,
    blackBackgroundsEnabled: Boolean = false
  ) {
    assertThat(this.theme).isEqualTo(theme)
    assertThat(this.availableThemes).isEqualTo(availableThemes)
    assertThat(this.materialYouEnabled).isEqualTo(materialYouEnabled)
    assertThat(this.materialYouVisible).isEqualTo(materialYouVisible)
    assertThat(this.blackBackgroundsEnabled).isEqualTo(blackBackgroundsEnabled)
  }
}
