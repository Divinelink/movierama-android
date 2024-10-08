package com.divinelink.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.divinelink.core.ui.TestTags

@Composable
fun LoadingContent(modifier: Modifier = Modifier) {
  Box(
    modifier = modifier
      .testTag(TestTags.LOADING_CONTENT)
      .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.50f))
      .fillMaxSize(),
  ) {
    Material3CircularProgressIndicator(
      modifier = Modifier
        .wrapContentSize()
        .align(Alignment.Center),
    )
  }
}
