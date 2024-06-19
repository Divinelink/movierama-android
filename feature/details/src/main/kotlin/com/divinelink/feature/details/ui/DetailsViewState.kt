package com.divinelink.feature.details.ui

import com.divinelink.core.model.account.AccountMediaDetails
import com.divinelink.core.model.details.MediaDetails
import com.divinelink.core.model.details.Movie
import com.divinelink.core.model.details.Review
import com.divinelink.core.model.details.TV
import com.divinelink.core.model.details.video.Video
import com.divinelink.core.model.media.MediaItem
import com.divinelink.core.model.media.MediaType
import com.divinelink.core.ui.UIText
import com.divinelink.core.ui.snackbar.SnackbarMessage

sealed interface AccountDetailsState {
  data object Loading : AccountDetailsState
  data object Unauthenticated : AccountDetailsState
  data class LoggedIn(val accountDetails: AccountMediaDetails) : AccountDetailsState
}


val AccountDetailsState.accountDetails: AccountMediaDetails
  get() = when (this) {
    is AccountDetailsState.Loading -> null
    is AccountDetailsState.Unauthenticated -> null
    is AccountDetailsState.LoggedIn -> accountDetails
  }

val AccountDetailsState?.watchlist: Boolean
  get() = when (this) {
    is AccountDetailsState.Loading -> false
    is AccountDetailsState.Unauthenticated -> false
    is AccountDetailsState.LoggedIn -> accountDetails.watchlist
    null -> false
  }

val AccountDetailsState.rating: String?
  get() = when (this) {
    is AccountDetailsState.Loading -> null
    is AccountDetailsState.Unauthenticated -> null
    is AccountDetailsState.LoggedIn -> accountDetails.beautifiedRating
  }

data class DetailsViewState(
  val isLoading: Boolean = false,
  val mediaType: MediaType,
  val mediaId: Int,
  val mediaDetails: MediaDetails? = null,
  val accountDetails: AccountDetailsState,
  val reviews: List<Review>? = null,
  val similarMovies: List<MediaItem.Media>? = null,
  val trailer: Video? = null,
  val error: UIText? = null,
  val snackbarMessage: SnackbarMessage? = null,
  val showRateDialog: Boolean = false,
  val navigateToLogin: Boolean? = null,
  val openShareDialog: Boolean = false,
) {
  val mediaItem = when (mediaDetails) {
    is Movie -> MediaItem.Media.Movie(
      id = mediaDetails.id,
      name = mediaDetails.title,
      posterPath = mediaDetails.posterPath,
      releaseDate = mediaDetails.releaseDate,
      rating = mediaDetails.rating,
      overview = mediaDetails.overview ?: "",
      isFavorite = mediaDetails.isFavorite,
    )
    is TV -> MediaItem.Media.TV(
      id = mediaDetails.id,
      name = mediaDetails.title,
      posterPath = mediaDetails.posterPath,
      releaseDate = mediaDetails.releaseDate,
      rating = mediaDetails.rating,
      overview = mediaDetails.overview ?: "",
      isFavorite = mediaDetails.isFavorite,
    )
    null -> null
  }

  private val urlTitle = mediaDetails
    ?.title
    ?.lowercase()
    ?.replace(":", "")
    ?.replace(regex = "[\\s|/]".toRegex(), replacement = "-")

  val shareUrl = "https://themoviedb.org/${mediaType.value}/$mediaId-$urlTitle"
}
