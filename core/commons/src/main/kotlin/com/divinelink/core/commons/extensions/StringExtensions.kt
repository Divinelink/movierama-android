package com.divinelink.core.commons.extensions

import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import java.text.SimpleDateFormat
import java.util.Locale

fun String.formatTo(
  inputFormat: String,
  outputFormat: String,
): String? {
  val input = SimpleDateFormat(inputFormat, Locale.ENGLISH)
  val output = SimpleDateFormat(outputFormat, Locale.ENGLISH)
  val date = input.parse(this)
  return date?.let { output.format(it) }
}

fun String?.extractDetailsFromDeepLink(): Pair<Int, String>? {
  // Example URL format: "https://www.themoviedb.org/tv/693134-dune-part-two"
  return this?.let {
    val segments = it.split("/")
    if (segments.size >= 4) {
      val mediaType = segments[3]
      val id = segments[4].substringBefore("-").toIntOrNull()
      id?.let { safeId ->
        return Pair(safeId, mediaType)
      }
    }
    return null
  }
}

fun calculateAge(
  fromDate: String,
  toDate: String? = null,
  clock: Clock = Clock.System,
): Int {
  // Parse the birthday string into a LocalDate object
  val birthDate = LocalDate.parse(fromDate)
  // Get the current date or the the date of death in the specified timezone
  val currentDate = if (toDate == null) {
    clock.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
  } else {
    LocalDate.parse(toDate)
  }

  // Calculate the age by finding the difference in years
  var age = currentDate.year - birthDate.year

  // Check if the birthday has not occurred yet this year, subtract one year if so
  if (currentDate < birthDate.plus(DatePeriod(years = age))) {
    age--
  }

  return age
}

fun String.calculateFourteenDayRange(clock: Clock = Clock.System): List<Pair<String, String>> {
  val startInstant = Instant.fromEpochSeconds(this.toLong())

  val startDate = startInstant.toLocalDateTime(TimeZone.currentSystemDefault()).date
  val currentDate = clock.now().toLocalDateTime(TimeZone.currentSystemDefault()).date

  val periods = mutableListOf<Pair<String, String>>()

  var periodStartDate = startDate

  while (periodStartDate <= currentDate) {
    // Calculate the end date for the current period (14 days after the start date)
    val periodEndDate = (periodStartDate + DatePeriod(days = 14)).coerceAtMost(currentDate)

    periods.add(
      Pair(
        periodStartDate.toString(),
        periodEndDate.toString(),
      ),
    )

    periodStartDate = periodEndDate + DatePeriod(days = 1)
  }

  return periods
}
