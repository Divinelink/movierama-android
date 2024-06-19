package com.divinelink.feature.details.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.hilt.navigation.compose.hiltViewModel
import com.divinelink.core.ui.TestTags
import com.divinelink.feature.details.navigation.DetailsGraph
import com.divinelink.feature.details.screens.destinations.DetailsScreenDestination
import com.divinelink.feature.details.ui.rate.RateModalBottomSheet
import com.divinelink.feature.settings.screens.destinations.AccountSettingsScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.delay

private const val BOTTOM_SHEET_DELAY = 200L

@OptIn(ExperimentalMaterial3Api::class)
@Destination<DetailsGraph>(
  start = true,
  navArgs = DetailsNavArguments::class
)
@Composable
fun DetailsScreen(
  navigator: DestinationsNavigator,
  viewModel: DetailsViewModel = hiltViewModel(),
) {
  val viewState = viewModel.viewState.collectAsState()
  var openBottomSheet by rememberSaveable { mutableStateOf(false) }

  LaunchedEffect(viewState.value.navigateToLogin) {
    viewState.value.navigateToLogin?.let {
      navigator.navigate(AccountSettingsScreenDestination)

      viewModel.consumeNavigateToLogin()
    }
  }
  val rateBottomSheetState = rememberModalBottomSheetState(
    skipPartiallyExpanded = true
  )

  LaunchedEffect(viewState.value.showRateDialog) {
    if (viewState.value.showRateDialog) {
      openBottomSheet = true
      delay(BOTTOM_SHEET_DELAY)
      rateBottomSheetState.show()
    } else {
      rateBottomSheetState.hide()
      openBottomSheet = false
    }
  }

  if (openBottomSheet) {
    RateModalBottomSheet(
      modifier = Modifier.testTag(TestTags.Details.RATE_DIALOG),
      sheetState = rateBottomSheetState,
      value = viewState.value.accountDetails.rating,
      mediaTitle = viewState.value.mediaDetails?.title ?: "",
      onSubmitRate = viewModel::onSubmitRate,
      onClearRate = viewModel::onClearRating,
      onRateChanged = {
        // TODO implement
      },
      onDismissRequest = viewModel::onDismissRateDialog,
      canClearRate = viewState.value.accountDetails.rating != null
    )
  }

  DetailsContent(
    viewState = viewState.value,
    onNavigateUp = navigator::popBackStack,
    onMarkAsFavoriteClicked = viewModel::onMarkAsFavorite,
    onSimilarMovieClicked = { movie ->
      val navArgs = DetailsNavArguments(
        id = movie.id,
        mediaType = movie.mediaType.value,
        isFavorite = movie.isFavorite ?: false,
      )
      val destination = DetailsScreenDestination(
        navArgs = navArgs,
      )

      navigator.navigate(destination)
    },
    onConsumeSnackbar = viewModel::consumeSnackbarMessage,
    onAddRateClicked = viewModel::onAddRateClicked,
    onAddToWatchlistClicked = viewModel::onAddToWatchlist,
    showOrHideShareDialog = viewModel::onShareClicked
  )
}
