package com.andreolas.movierama.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bookmarks
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.rounded.Bookmarks
import androidx.compose.material.icons.rounded.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.andreolas.movierama.R
import com.andreolas.movierama.destinations.DirectionDestination
import com.andreolas.movierama.destinations.HomeScreenDestination
import com.andreolas.movierama.destinations.SettingsScreenDestination

enum class TopLevelDestination(
  val selectedIcon: ImageVector,
  val unselectedIcon: ImageVector,
  val iconTextId: Int,
  val titleTextId: Int,
  val destination: DirectionDestination
) {
  HOME(
    selectedIcon = Icons.Rounded.Home,
    unselectedIcon = Icons.Outlined.Home,
    iconTextId = R.string.home,
    titleTextId = R.string.home,
    destination = HomeScreenDestination
  ),
  WATCHLIST(
    selectedIcon = Icons.Rounded.Bookmarks,
    unselectedIcon = Icons.Outlined.Bookmarks,
    iconTextId = R.string.watchlist, // TODO Add resources from the feature module
    titleTextId = R.string.watchlist,
    destination = SettingsScreenDestination
  )
}
