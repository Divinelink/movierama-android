package com.divinelink.core.ui.components

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.divinelink.core.designsystem.theme.AppTheme
import com.divinelink.core.designsystem.theme.dimensions
import com.divinelink.core.ui.R
import com.valentinilk.shimmer.shimmer

@Composable
fun WatchlistButton(
  modifier: Modifier = Modifier,
  onWatchlist: Boolean,
  onClick: () -> Unit,
) {
  Crossfade(
    targetState = onWatchlist,
    label = "Watchlist button",
  ) { isOnWatchlist ->
    when (isOnWatchlist) {
      true -> {
        OutlinedButton(
          modifier = modifier.fillMaxWidth(),
          onClick = onClick
        ) {
          Icon(
            imageVector = Icons.Filled.Done,
            contentDescription = null
          )

          Spacer(modifier = Modifier.width(MaterialTheme.dimensions.keyline_4))

          Text(text = stringResource(id = R.string.details__added_to_watchlist_button))
        }
      }
      false -> {
        Button(
          modifier = modifier.fillMaxWidth(),
          onClick = onClick
        ) {
          Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = null
          )

          Spacer(modifier = Modifier.width(MaterialTheme.dimensions.keyline_4))

          Text(text = stringResource(id = R.string.details__add_to_watchlist_button))
        }
      }
    }
  }
}

@Composable
fun WatchlistButtonSkeleton(
  modifier: Modifier = Modifier,
) {
  Box(
    modifier = modifier
      .clip(ButtonDefaults.shape)
      .shimmer()
      .background(MaterialTheme.colorScheme.primary)
      .fillMaxWidth()
      .height(MaterialTheme.dimensions.keyline_40)
  )
}

@Preview
@Composable
private fun WatchlistButtonPreview() {
  AppTheme {
    Column(
      verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.keyline_8),
    ) {
      WatchlistButton(onWatchlist = false, onClick = {})
      WatchlistButton(onWatchlist = true, onClick = {})
      WatchlistButtonSkeleton()
    }
  }
}
