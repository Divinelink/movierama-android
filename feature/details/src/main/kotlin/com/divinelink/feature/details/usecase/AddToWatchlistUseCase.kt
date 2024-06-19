package com.divinelink.feature.details.usecase

import com.divinelink.core.commons.di.IoDispatcher
import com.divinelink.core.commons.domain.FlowUseCase
import com.divinelink.core.data.details.repository.DetailsRepository
import com.divinelink.core.data.session.model.SessionException
import com.divinelink.core.datastore.SessionStorage
import com.divinelink.core.model.media.MediaType
import com.divinelink.core.network.media.model.details.watchlist.AddToWatchlistRequestApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import javax.inject.Inject

data class AddToWatchlistParameters(
  val id: Int,
  val mediaType: MediaType,
  val addToWatchlist: Boolean,
)

open class AddToWatchlistUseCase @Inject constructor(
  private val sessionStorage: SessionStorage,
  private val repository: DetailsRepository,
  @IoDispatcher val dispatcher: CoroutineDispatcher,
) : FlowUseCase<AddToWatchlistParameters, Boolean>(dispatcher) {
  override fun execute(parameters: AddToWatchlistParameters): Flow<Result<Boolean>> = flow {
    val accountId = sessionStorage.accountId.first()
    val sessionId = sessionStorage.sessionId

    if (accountId == null || sessionId == null) {
      emit(Result.failure(SessionException.InvalidAccountId()))
      return@flow
    } else {

      val request = when (parameters.mediaType) {
        MediaType.MOVIE -> AddToWatchlistRequestApi.Movie(
          movieId = parameters.id,
          accountId = accountId,
          addToWatchlist = parameters.addToWatchlist,
          sessionId = sessionId,
        )
        MediaType.TV -> AddToWatchlistRequestApi.TV(
          seriesId = parameters.id,
          accountId = accountId,
          addToWatchlist = parameters.addToWatchlist,
          sessionId = sessionId,
        )

        else -> throw IllegalArgumentException("Unsupported media type: ${parameters.mediaType}")
      }

      val response = repository.addToWatchlist(request).last()

      response.fold(
        onSuccess = { emit(Result.success(it.added)) },
        onFailure = { emit(Result.failure(it)) }
      )
    }
  }
}
