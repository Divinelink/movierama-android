package com.divinelink.feature.settings.app.help

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.divinelink.core.commons.BuildConfigProvider
import com.divinelink.core.testing.ComposeTest
import com.divinelink.core.testing.getString
import com.divinelink.core.testing.navigator.FakeDestinationsNavigator
import com.divinelink.core.testing.setContentWithTheme
import com.divinelink.core.ui.TestTags
import com.divinelink.feature.settings.R
import com.divinelink.feature.settings.app.SettingsScreen
import com.divinelink.feature.settings.screens.destinations.HelpSettingsScreenDestination
import com.divinelink.feature.settings.screens.destinations.SettingsScreenDestination
import kotlin.test.Test
import com.divinelink.core.commons.R as CommonR

class HelpSettingsScreenTest : ComposeTest() {

  private val releaseBuildConfigProvider = object : BuildConfigProvider {
    override val isDebug: Boolean = false
    override val buildType: String = "release"
  }

  private val debugBuildConfigProvider = object : BuildConfigProvider {
    override val isDebug: Boolean = true
    override val buildType: String = "debug"
  }

  @Test
  fun `test version with debug build`() {
    setContentWithTheme {
      HelpSettingsScreen(
        navigator = FakeDestinationsNavigator(),
        buildConfigProvider = debugBuildConfigProvider,
      )
    }

    val version = getString(CommonR.string.version_name) + " debug"

    with(composeTestRule) {
      onNodeWithText(getString(R.string.HelpSettingsFragment__help))
        .assertIsDisplayed()
        .assertTextEquals("Help")

      onNodeWithText(getString(R.string.HelpSettingsFragment__version)).assertIsDisplayed()
      onNodeWithText(version).assertIsDisplayed()
    }
  }

  @Test
  fun `test version with release`() {
    setContentWithTheme {
      HelpSettingsScreen(
        navigator = FakeDestinationsNavigator(),
        buildConfigProvider = releaseBuildConfigProvider,
      )
    }

    val version = getString(CommonR.string.version_name)

    with(composeTestRule) {
      onNodeWithText(getString(R.string.HelpSettingsFragment__version)).assertIsDisplayed()
      onNodeWithText(version).assertIsDisplayed()
    }
  }

  @Test
  fun `test navigateUp`() {
    val navigator = FakeDestinationsNavigator()
    navigator.navigate(SettingsScreenDestination)
    setContentWithTheme {
      SettingsScreen(navigator = navigator)
    }

    with(composeTestRule) {
      onNodeWithText(getString(R.string.preferences__help)).performClick()

      navigator.verifyNavigatedToDirection(HelpSettingsScreenDestination)

      onNodeWithTag(TestTags.Settings.NAVIGATION_ICON).performClick().assertIsDisplayed()
    }

    navigator.verifyNavigatedToDirection(SettingsScreenDestination)
  }
}
