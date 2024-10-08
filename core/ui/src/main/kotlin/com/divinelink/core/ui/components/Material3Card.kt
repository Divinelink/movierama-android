package com.divinelink.core.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.divinelink.core.designsystem.theme.MaterialCardShape

@Composable
fun Material3Card(
  modifier: Modifier = Modifier,
  shape: Shape = MaterialCardShape,
  backgroundColor: Color = MaterialTheme.colorScheme.surface,
  contentColor: Color = MaterialTheme.colorScheme.contentColorFor(backgroundColor),
  border: BorderStroke? = null,
  elevation: Dp = 1.dp,
  content: @Composable () -> Unit,
) {
  Surface(
    modifier = modifier,
    shape = shape,
    color = backgroundColor,
    contentColor = contentColor,
    tonalElevation = elevation,
    border = border,
    content = content,
  )
}
