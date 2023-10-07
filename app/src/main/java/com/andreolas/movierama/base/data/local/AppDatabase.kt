package com.andreolas.movierama.base.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.andreolas.movierama.base.data.local.popular.PersistableMovie
import com.andreolas.movierama.base.data.local.popular.MovieDAO
import com.andreolas.movierama.base.data.local.popular.PersistableTV

@Database(
  entities = [
    PersistableMovie::class,
    PersistableTV::class
  ],
  version = AppDatabase.LATEST_VERSION,
  exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

  abstract fun movieDAO(): MovieDAO

  companion object {
    const val DB_NAME = "App_Database"
    const val LATEST_VERSION = 4
  }
}
