package com.divinelink.core.model.media

import com.divinelink.core.commons.extensions.round
import com.divinelink.core.model.person.Gender

sealed class MediaItem(
  open val id: Int,
  open val name: String,
  open val posterPath: String?,
  open val mediaType: MediaType,
) {

  sealed class Media(
    override val id: Int,
    override val name: String,
    override val posterPath: String?,
    open val releaseDate: String,
    open val rating: String,
    open val overview: String,
    open val isFavorite: Boolean?,
    override val mediaType: MediaType,
  ) : MediaItem(
    id = id,
    posterPath = posterPath,
    name = name,
    mediaType = mediaType,
  ) {
    val decimalRating: String
      get() = this.rating.toDouble().round(1).toString()

    data class TV(
      override val id: Int,
      override val name: String,
      override val posterPath: String?,
      override val releaseDate: String,
      override val rating: String,
      override val overview: String,
      override val isFavorite: Boolean?,
    ) : Media(
      id = id,
      posterPath = posterPath,
      name = name,
      releaseDate = releaseDate,
      rating = rating,
      overview = overview,
      isFavorite = isFavorite,
      mediaType = MediaType.TV,
    )

    data class Movie(
      override val id: Int,
      override val name: String,
      override val posterPath: String?,
      override val releaseDate: String,
      override val rating: String,
      override val overview: String,
      override val isFavorite: Boolean?,
    ) : Media(
      id = id,
      posterPath = posterPath,
      name = name,
      releaseDate = releaseDate,
      rating = rating,
      overview = overview,
      isFavorite = isFavorite == true,
      mediaType = MediaType.MOVIE,
    )
  }

  data class Person(
    override val id: Int,
    override val name: String,
    val profilePath: String?,
    val gender: Gender,
    val knownForDepartment: String?,
  ) : MediaItem(
    id = id,
    name = name,
    posterPath = profilePath,
    mediaType = MediaType.PERSON,
  )

  data object Unknown : MediaItem(
    id = -1,
    posterPath = null,
    name = "",
    mediaType = MediaType.UNKNOWN,
  )
}
