package com.divinelink.feature.details.media.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.StarRate
import androidx.compose.material.icons.rounded.WatchLater
import androidx.compose.runtime.Composable
import com.divinelink.core.model.details.DetailActionItem
import com.divinelink.core.ui.IconWrapper
import com.divinelink.core.ui.UIText
import com.divinelink.core.ui.components.expandablefab.ExpandableFloatActionButton
import com.divinelink.core.ui.components.expandablefab.FloatingActionButtonItem
import com.divinelink.feature.details.R
import com.divinelink.core.ui.R as uiR

@Composable
internal fun DetailsExpandableFloatingActionButton(
  actionButtons: List<DetailActionItem>,
  onAddRateClicked: () -> Unit,
  onAddToWatchlistClicked: () -> Unit,
  onRequestClicked: () -> Unit,
) {
  ExpandableFloatActionButton(
    buttons = actionButtons.map { button ->
      when (button) {
        DetailActionItem.RATE -> FloatingActionButtonItem(
          icon = IconWrapper.Vector(Icons.Rounded.StarRate),
          label = UIText.ResourceText(R.string.details__add_rating),
          contentDescription = UIText.ResourceText(R.string.details__add_rating),
          onClick = onAddRateClicked,
        )
        DetailActionItem.WATCHLIST -> FloatingActionButtonItem(
          icon = IconWrapper.Vector(Icons.Rounded.WatchLater),
          label = UIText.StringText("Watchlist"),
          contentDescription = UIText.StringText("Watchlist"),
          onClick = onAddToWatchlistClicked,
        )
        DetailActionItem.REQUEST -> FloatingActionButtonItem(
          icon = IconWrapper.Image(uiR.drawable.core_ui_ic_jellyseerr),
          label = UIText.ResourceText(R.string.feature_details_request),
          contentDescription = UIText.ResourceText(R.string.feature_details_request),
          onClick = onRequestClicked,
        )
      }
    },
  )
}
