package com.divinelink.core.model.credits

import kotlinx.serialization.Serializable

@Serializable
sealed class PersonRole(val title: String?) {

  @Serializable
  data class SeriesActor(
    val character: String?,
    val creditId: String? = null,
    val totalEpisodes: Int? = null,
  ) : PersonRole(character)

  @Serializable
  data class MovieActor(val character: String?) : PersonRole(character)

  @Serializable
  data class Crew(
    val job: String?,
    val creditId: String?,
    val totalEpisodes: Long? = null,
    val department: String? = null,
  ) : PersonRole(job)

  @Serializable
  data class MovieCrew(
    val job: String?,
    val creditId: String?,
    val department: String? = null,
  ) : PersonRole(job)

  data object Director : PersonRole(null)

  data object Creator : PersonRole(null)

  data object Unknown : PersonRole(null)
}
