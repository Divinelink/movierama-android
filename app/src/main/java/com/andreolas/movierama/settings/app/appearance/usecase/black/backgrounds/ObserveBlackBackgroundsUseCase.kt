package com.andreolas.movierama.settings.app.appearance.usecase.black.backgrounds

import com.andreolas.movierama.base.di.DefaultDispatcher
import com.andreolas.movierama.base.storage.PreferenceStorage
import gr.divinelink.core.util.domain.FlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

open class ObserveBlackBackgroundsUseCase @Inject constructor(
  private val preferenceStorage: PreferenceStorage,
  @DefaultDispatcher dispatcher: CoroutineDispatcher
) : FlowUseCase<Unit, Boolean>(dispatcher) {
  override fun execute(parameters: Unit): Flow<Result<Boolean>> {
    return preferenceStorage.isBlackBackgroundsEnabled.map {
      Result.success(it)
    }
  }
}