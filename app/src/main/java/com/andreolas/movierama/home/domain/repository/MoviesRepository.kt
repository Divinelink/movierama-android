package com.andreolas.movierama.home.domain.repository

import com.andreolas.movierama.base.data.remote.movies.dto.popular.PopularRequestApi
import com.andreolas.movierama.base.data.remote.movies.dto.search.movie.SearchRequestApi
import com.andreolas.movierama.base.data.remote.movies.dto.search.multi.MultiSearchRequestApi
import com.andreolas.movierama.home.domain.model.MediaItem
import com.andreolas.movierama.home.domain.model.PopularMovie
import gr.divinelink.core.util.domain.Result
import kotlinx.coroutines.flow.Flow

typealias MultiListResult = Result<List<MediaItem>>
typealias MediaListResult = Result<List<MediaItem.Media>>

/**
 * The data layer for any requests related to Popular Movies.
 */
interface MoviesRepository {

  /**
   * Request popular movies using pagination.
   * Uses [Flow] in order to observe changes to our popular movies list.
   */
  fun fetchPopularMovies(
    request: PopularRequestApi,
  ): Flow<MediaListResult>

  /**
   * Fetch all popular movies that the user has marked as favorite.
   */
  fun fetchFavoriteMovies(): Flow<MediaListResult>

  /**
   * Fetch all favorite movie ids.
   * Uses [Flow] in order to observe changes to our favorite movies list.
   */
  fun fetchFavoriteMoviesIds(): Flow<Result<List<Int>>>

  /**
   * Fetch all favorite ids. Movies, series, persons.
   * Uses [Flow] in order to observe changes to our favorite list.
   */
  fun fetchFavoriteIds(): Flow<Result<List<Int>>>

  /**
   * Request movies through a search query. Uses pagination.
   * Uses [Flow] in order to observe changes to our movies list.
   */
  fun fetchSearchMovies(
    request: SearchRequestApi,
  ): Flow<MediaListResult>

  /**
   * Request movies, tv series and persons through a search query.
   */
  fun fetchMultiInfo(
    requestApi: MultiSearchRequestApi,
  ): Flow<MultiListResult>

  /**
   * Add favorite [movie] to local storage.
   */
  suspend fun insertFavoriteMovie(
    movie: PopularMovie,
  ): Result<Unit>

  /**
   * Remove favorite movie using its [id] from local storage.
   */
  suspend fun removeFavoriteMovie(
    id: Int,
  ): Result<Unit>

  suspend fun checkIfFavorite(
    id: Int,
  ): Result<Boolean>
}
