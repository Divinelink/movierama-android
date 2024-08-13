package com.divinelink.core.database.person

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.divinelink.core.commons.di.IoDispatcher
import com.divinelink.core.database.Database
import com.divinelink.core.database.currentEpochSeconds
import com.divinelink.core.database.person.credits.PersonCastCreditEntity
import com.divinelink.core.database.person.credits.PersonCreditsEntity
import com.divinelink.core.database.person.credits.PersonCrewCreditEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.datetime.Clock
import javax.inject.Inject

class ProdPersonDao @Inject constructor(
  private val clock: Clock,
  private val database: Database,
  @IoDispatcher private val dispatcher: CoroutineDispatcher,
) : PersonDao {

  override fun fetchPersonById(id: Long): Flow<PersonEntity?> = database
    .personEntityQueries
    .fetchPersonById(id)
    .asFlow()
    .mapToOneOrNull(context = dispatcher)

  override fun insertPerson(person: PersonEntity) = database
    .personEntityQueries
    .insertPerson(person)

  override fun insertPersonCredits(id: Long) {
    val currentEpochSeconds = clock.currentEpochSeconds()

    database
      .personCreditsEntityQueries
      .insertPersonCredits(
        PersonCreditsEntity = PersonCreditsEntity(
          id = id,
          insertedAt = currentEpochSeconds,
        ),
      )
  }

  override fun insertPersonCastCredits(cast: List<PersonCastCreditEntity>) = database.transaction {
    cast.forEach {
      database.personCastCreditEntityQueries.insertPersonCastCredit(it)
    }
  }

  override fun insertPersonCrewCredits(crew: List<PersonCrewCreditEntity>) = database.transaction {
    crew.forEach {
      database.personCrewCreditEntityQueries.insertPersonCrewCredit(it)
    }
  }

  override fun fetchPersonCombinedCredits(id: Long): Flow<PersonCombinedCreditsEntity?> {
    val personCreditId = fetchPersonCredits(id)
    val castCredits = fetchPersonCastCredits(id)
    val crewCredits = fetchPersonCrewCredits(id)

    return combine(personCreditId, castCredits, crewCredits) { personCredit, cast, crew ->
      if (personCredit != null) {
        PersonCombinedCreditsEntity(
          id = id,
          cast = cast,
          crew = crew,
        )
      } else {
        null
      }
    }
  }

  private fun fetchPersonCredits(id: Long): Flow<PersonCreditsEntity?> = database
    .personCreditsEntityQueries
    .fetchPersonCredits(id)
    .asFlow()
    .mapToOneOrNull(context = dispatcher)

  private fun fetchPersonCastCredits(id: Long): Flow<List<PersonCastCreditEntity>> = database
    .personCastCreditEntityQueries
    .fetchPersonCastCredit(id)
    .asFlow()
    .mapToList(context = dispatcher)

  override fun fetchTopPopularCastCredits(id: Long): Flow<List<PersonCastCreditEntity>> = database
    .personCastCreditEntityQueries
    .fetchPopularPersonCastCredits(id)
    .asFlow()
    .mapToList(context = dispatcher)

  private fun fetchPersonCrewCredits(id: Long): Flow<List<PersonCrewCreditEntity>> = database
    .personCrewCreditEntityQueries
    .fetchPersonCrewCredit(id)
    .asFlow()
    .mapToList(context = dispatcher)
}
