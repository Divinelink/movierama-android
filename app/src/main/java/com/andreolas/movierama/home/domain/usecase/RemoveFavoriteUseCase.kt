package com.andreolas.movierama.home.domain.usecase

import com.andreolas.movierama.base.di.IoDispatcher
import com.andreolas.movierama.home.domain.model.MediaType
import com.andreolas.movierama.home.domain.repository.MoviesRepository
import gr.divinelink.core.util.domain.Result
import gr.divinelink.core.util.domain.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

@Suppress("ThrowingExceptionsWithoutMessageOrCause")
@Deprecated("Use MarkAsFavoriteUseCase instead")
open class RemoveFavoriteUseCase @Inject constructor(
  private val repository: MoviesRepository,
  @IoDispatcher dispatcher: CoroutineDispatcher,
) : UseCase<Int, Unit>(dispatcher) {
  override suspend fun execute(parameters: Int) {
    val result = repository.removeFavoriteMedia(parameters, MediaType.MOVIE)

    when (result) {
      is Result.Success -> result.data
      is Result.Error -> throw result.exception
      Result.Loading -> throw IllegalStateException()
    }
  }
}
