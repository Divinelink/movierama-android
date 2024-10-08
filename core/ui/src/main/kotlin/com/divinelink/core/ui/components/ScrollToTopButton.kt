package com.divinelink.core.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import com.divinelink.core.designsystem.theme.AppTheme
import com.divinelink.core.designsystem.theme.dimensions
import com.divinelink.core.ui.Previews
import com.divinelink.core.ui.R
import com.divinelink.core.ui.TestTags

@Composable
fun ScrollToTopButton(onClick: () -> Unit) {
  Button(
    modifier = Modifier
      .testTag(TestTags.SCROLL_TO_TOP_BUTTON)
      .shadow(MaterialTheme.dimensions.keyline_4, shape = CircleShape)
      .size(MaterialTheme.dimensions.keyline_56)
      .clip(shape = CircleShape),
    contentPadding = PaddingValues(),
    onClick = onClick,
  ) {
    Icon(
      imageVector = Icons.Filled.KeyboardArrowUp,
      contentDescription = stringResource(R.string.core_ui_scroll_to_top_button),
    )
  }
}

@Composable
@Previews
private fun ScrollToTopButtonPreview() {
  AppTheme {
    ScrollToTopButton {}
  }
}
