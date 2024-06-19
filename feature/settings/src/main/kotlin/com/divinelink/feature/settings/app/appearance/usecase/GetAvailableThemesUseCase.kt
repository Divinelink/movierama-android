package com.divinelink.feature.settings.app.appearance.usecase

import android.os.Build
import com.divinelink.core.commons.di.MainDispatcher
import com.divinelink.core.commons.domain.UseCase
import com.divinelink.core.designsystem.theme.Theme
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetAvailableThemesUseCase @Inject constructor(
  @MainDispatcher dispatcher: CoroutineDispatcher,
) : UseCase<Unit, List<Theme>>(dispatcher) {

  override suspend fun execute(parameters: Unit): List<Theme> = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> {
      listOf(Theme.SYSTEM, Theme.LIGHT, Theme.DARK)
    }
    else -> {
      listOf(Theme.LIGHT, Theme.DARK)
    }
  }
}