package com.divinelink.core.data.details.repository

import com.divinelink.core.commons.di.IoDispatcher
import com.divinelink.core.data.details.mapper.api.map
import com.divinelink.core.data.details.mapper.api.toSeriesCastEntity
import com.divinelink.core.data.details.mapper.api.toSeriesCastRoleEntity
import com.divinelink.core.data.details.mapper.api.toSeriesCrewEntity
import com.divinelink.core.data.details.mapper.api.toSeriesCrewJobEntity
import com.divinelink.core.data.details.mapper.map
import com.divinelink.core.data.details.model.MediaDetailsException
import com.divinelink.core.data.details.model.ReviewsException
import com.divinelink.core.data.details.model.SimilarException
import com.divinelink.core.data.details.model.VideosException
import com.divinelink.core.database.credits.dao.CreditsDao
import com.divinelink.core.model.account.AccountMediaDetails
import com.divinelink.core.model.credits.AggregateCredits
import com.divinelink.core.model.details.MediaDetails
import com.divinelink.core.model.details.Review
import com.divinelink.core.model.details.video.Video
import com.divinelink.core.model.media.MediaItem
import com.divinelink.core.model.media.MediaType
import com.divinelink.core.network.media.model.credits.AggregateCreditsApi
import com.divinelink.core.network.media.model.details.DetailsRequestApi
import com.divinelink.core.network.media.model.details.reviews.ReviewsRequestApi
import com.divinelink.core.network.media.model.details.reviews.toDomainReviewsList
import com.divinelink.core.network.media.model.details.similar.SimilarRequestApi
import com.divinelink.core.network.media.model.details.similar.toDomainMoviesList
import com.divinelink.core.network.media.model.details.toDomainMedia
import com.divinelink.core.network.media.model.details.videos.VideosRequestApi
import com.divinelink.core.network.media.model.details.videos.toDomainVideosList
import com.divinelink.core.network.media.model.details.watchlist.AddToWatchlistRequestApi
import com.divinelink.core.network.media.model.rating.AddRatingRequestApi
import com.divinelink.core.network.media.model.rating.DeleteRatingRequestApi
import com.divinelink.core.network.media.model.states.AccountMediaDetailsRequestApi
import com.divinelink.core.network.media.service.MediaService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProdDetailsRepository @Inject constructor(
  private val mediaRemote: MediaService,
  private val creditsDao: CreditsDao,
  @IoDispatcher val dispatcher: CoroutineDispatcher,
) : DetailsRepository {

  override fun fetchMovieDetails(request: DetailsRequestApi): Flow<Result<MediaDetails>> =
    mediaRemote
      .fetchDetails(request)
      .map { apiResponse ->
        Result.success(apiResponse.toDomainMedia())
      }.catch {
        throw MediaDetailsException()
      }

  override fun fetchMovieReviews(request: ReviewsRequestApi): Flow<Result<List<Review>>> =
    mediaRemote
      .fetchReviews(request)
      .map { apiResponse ->
        Result.success(apiResponse.toDomainReviewsList())
      }.catch {
        throw ReviewsException()
      }

  override fun fetchSimilarMovies(
    request: SimilarRequestApi,
  ): Flow<Result<List<MediaItem.Media>>> = mediaRemote
    .fetchSimilarMovies(request)
    .map { apiResponse ->
      Result.success(apiResponse.toDomainMoviesList(MediaType.from(request.endpoint)))
    }.catch {
      throw SimilarException()
    }

  override fun fetchVideos(request: VideosRequestApi): Flow<Result<List<Video>>> = mediaRemote
    .fetchVideos(request)
    .map { apiResponse ->
      Result.success(apiResponse.toDomainVideosList())
    }.catch {
      throw VideosException()
    }

  override fun fetchAccountMediaDetails(
    request: AccountMediaDetailsRequestApi,
  ): Flow<Result<AccountMediaDetails>> = mediaRemote
    .fetchAccountMediaDetails(request)
    .map { response ->
      Result.success(response.map())
    }

  override fun submitRating(request: AddRatingRequestApi): Flow<Result<Unit>> = mediaRemote
    .submitRating(request)
    .map {
      Result.success(Unit)
    }

  override fun deleteRating(request: DeleteRatingRequestApi): Flow<Result<Unit>> = mediaRemote
    .deleteRating(request)
    .map {
      Result.success(Unit)
    }

  override fun addToWatchlist(request: AddToWatchlistRequestApi): Flow<Result<Unit>> = mediaRemote
    .addToWatchlist(request)
    .map {
      Result.success(Unit)
    }

  private fun insertLocalAggregateCredits(aggregateCredits: AggregateCreditsApi) {
    creditsDao.insertAggregateCredits(aggregateCredits.id)
    creditsDao.insertCastRoles(aggregateCredits.toSeriesCastRoleEntity())
    creditsDao.insertCast(aggregateCredits.toSeriesCastEntity())
    creditsDao.insertCrewJobs(aggregateCredits.toSeriesCrewJobEntity())
    creditsDao.insertCrew(aggregateCredits.toSeriesCrewEntity())
  }

  override fun fetchLocalAggregateCredits(id: Long): Flow<Result<AggregateCredits>> = creditsDao
    .fetchAllCredits(id)
    .map { localCredits ->
      Result.success(localCredits.map())
    }

  override fun fetchRemoteAggregateCredits(id: Long): Flow<Result<AggregateCredits>> = mediaRemote
    .fetchAggregatedCredits(id)
    .map { apiResponse ->
      insertLocalAggregateCredits(apiResponse)
      Result.success(apiResponse.map())
    }

  override fun fetchAggregateCredits(id: Long): Flow<Result<AggregateCredits>> = flow {
    try {
      val localExists = creditsDao.checkIfAggregateCreditsExist(id).first()
      val result = if (localExists) {
        fetchLocalAggregateCredits(id).first()
      } else {
        fetchRemoteAggregateCredits(id).first()
      }
      emit(result)
    } catch (e: Exception) {
      emit(Result.failure(e))
    }
  }.flowOn(dispatcher)
}
