package com.andreolas.movierama.settings.app

import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.andreolas.ComposeTest
import com.andreolas.movierama.R
import com.andreolas.movierama.destinations.AccountSettingsScreenDestination
import com.andreolas.movierama.destinations.AppearanceSettingsScreenDestination
import com.andreolas.movierama.destinations.HelpSettingsScreenDestination
import com.andreolas.movierama.destinations.SettingsScreenDestination
import com.andreolas.movierama.fakes.FakeDestinationsNavigator
import org.junit.Test

class SettingsScreenTest : ComposeTest() {

  @Test
  fun `test navigate to account screen and navigate up`() {
    val destinationsNavigator = FakeDestinationsNavigator()

    destinationsNavigator.navigate(
      direction = SettingsScreenDestination()
    )

    composeTestRule.setContent {
      SettingsScreen(
        navigator = destinationsNavigator
      )
    }

    with(composeTestRule) {
      onNodeWithText("Account").assertExists()

      onNodeWithText("Account").performClick()
    }

    destinationsNavigator.verifyNavigatedToDirection(
      AccountSettingsScreenDestination
    )

    val navigateUpContentDescription = composeTestRule.activity
      .getString(R.string.navigate_up_button_content_description)

    with(composeTestRule) {
      onNodeWithContentDescription(navigateUpContentDescription).performClick()
    }

    destinationsNavigator.verifyNavigatedToDirection(
      SettingsScreenDestination
    )
  }

  @Test
  fun `test navigate to appearance screen`() {
    val destinationsNavigator = FakeDestinationsNavigator()

    destinationsNavigator.navigate(
      direction = SettingsScreenDestination()
    )

    composeTestRule.setContent {
      SettingsScreen(
        navigator = destinationsNavigator
      )
    }

    val appearanceString = composeTestRule.activity.getString(R.string.preferences__appearance)

    with(composeTestRule) {
      onNodeWithText(appearanceString).assertExists()

      onNodeWithText(appearanceString).performClick()
    }

    destinationsNavigator.verifyNavigatedToDirection(
      AppearanceSettingsScreenDestination
    )
  }

  @Test
  fun `test navigate to help screen`() {
    val destinationsNavigator = FakeDestinationsNavigator()

    destinationsNavigator.navigate(
      direction = SettingsScreenDestination()
    )

    composeTestRule.setContent {
      SettingsScreen(
        navigator = destinationsNavigator
      )
    }

    val helpString = composeTestRule.activity.getString(R.string.preferences__help)

    with(composeTestRule) {
      onNodeWithText(helpString).assertExists()

      onNodeWithText(helpString).performClick()
    }

    destinationsNavigator.verifyNavigatedToDirection(
      HelpSettingsScreenDestination
    )
  }
}