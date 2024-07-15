package com.divinelink.core.model.details

import com.divinelink.core.model.credits.PersonRole

data class Person(
  val id: Long,
  val name: String,
  val profilePath: String?,
  val role: PersonRole,
)
