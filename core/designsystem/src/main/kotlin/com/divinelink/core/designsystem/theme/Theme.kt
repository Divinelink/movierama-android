package com.divinelink.core.designsystem.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat

@Composable
fun AppTheme(
  useDarkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = false,
  blackBackground: Boolean = true,
  content: @Composable () -> Unit,
) {
  var colors = if (dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
    val context = LocalContext.current
    if (useDarkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
  } else {
    if (useDarkTheme) DarkColors else LightColors
  }

  if (blackBackground && useDarkTheme) {
    colors = colors.copy(background = Color.Black, surface = Color.Black)
  }

  val view = LocalView.current
  if (!view.isInEditMode) {
    SideEffect {
      val window = (view.context as Activity).window
      window.statusBarColor = colors.background.toArgb()
      window.navigationBarColor = colors.background.toArgb()
      WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !useDarkTheme
      WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = !useDarkTheme
    }
  }

  CompositionLocalProvider(
    LocalDarkThemeProvider provides useDarkTheme,
  ) {
    MaterialTheme(
      colorScheme = colors,
      typography = AppTypography,
      content = content,
    )
  }
}

val LocalDarkThemeProvider = staticCompositionLocalOf { false }

@Composable
fun ColorScheme.textColorDisabled(): Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)

val SearchBarSize = 48.dp

val ListPaddingValues = PaddingValues(
  top = 16.dp,
  start = 12.dp,
  end = 12.dp,
  bottom = 16.dp,
)

enum class Theme(val storageKey: String) {
  SYSTEM("system"),
  LIGHT("light"),
  DARK("dark"),
}

/**
 * Returns the matching [Theme] for the given [storageKey] value.
 */
fun themeFromStorageKey(storageKey: String): Theme? =
  Theme.entries.firstOrNull { it.storageKey == storageKey }
