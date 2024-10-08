package com.divinelink.core.testing.factories.database.credits.crew

import com.divinelink.core.database.credits.crew.SeriesCrew
import com.divinelink.core.testing.factories.database.credits.AggregateCreditsFactory

object SeriesCrewFactory {

  fun randallEinhorn() = SeriesCrew(
    id = 1215572,
    name = "Randall Einhorn",
    originalName = "Randall Einhorn",
    job = "Director of Photography",
    profilePath = null,
    totalEpisodeCount = 3,
    aggregateCreditId = AggregateCreditsFactory.theOffice().id,
    knownForDepartment = "Directing",
    gender = 2,
    department = "Camera",
  )

  fun daleAlexander() = SeriesCrew(
    id = 1879373,
    name = "Dale Alexander",
    originalName = "Dale Alexander",
    job = "Key Grip",
    profilePath = null,
    totalEpisodeCount = 3,
    aggregateCreditId = AggregateCreditsFactory.theOffice().id,
    knownForDepartment = "Camera",
    gender = 0,
    department = "Camera",
  )

  fun ronNichols() = SeriesCrew(
    id = 2166021,
    name = "Ron Nichols",
    originalName = "Ron Nichols",
    job = "Key Grip",
    profilePath = null,
    totalEpisodeCount = 1,
    aggregateCreditId = AggregateCreditsFactory.theOffice().id,
    knownForDepartment = "Camera",
    gender = 0,
    department = "Camera",
  )

  fun peterSmokler() = SeriesCrew(
    id = 67864,
    name = "Peter Smokler",
    originalName = "Peter Smokler",
    job = "Director of Photography",
    profilePath = null,
    totalEpisodeCount = 1,
    aggregateCreditId = AggregateCreditsFactory.theOffice().id,
    knownForDepartment = "Camera",
    gender = 2,
    department = "Camera",
  )

  fun cameraDepartment() = listOf(
    daleAlexander(),
    randallEinhorn(),
    peterSmokler(),
    ronNichols(),
  )
}
