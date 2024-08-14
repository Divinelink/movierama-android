package com.divinelink.core.database.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.divinelink.core.database.Database
import com.divinelink.core.database.credits.dao.CreditsDao
import com.divinelink.core.database.credits.dao.ProdCreditsDao
import com.divinelink.core.database.person.PersonDao
import com.divinelink.core.database.person.ProdPersonDao
import org.koin.dsl.module

val databaseModule = module {

  single<SqlDriver> {
    AndroidSqliteDriver(
      schema = Database.Schema,
      context = get(),
      name = "database.db",
    )
  }

  single<Database> { Database(get()) }

  single<CreditsDao> {
    ProdCreditsDao(
      database = get(),
      clock = get(),
      dispatcher = get(),
    )
  }

  single<PersonDao> {
    ProdPersonDao(
      database = get(),
      dispatcher = get(),
      clock = get(),
    )
  }

  single { get<Database>().jellyseerrAccountDetailsQueries }
}
