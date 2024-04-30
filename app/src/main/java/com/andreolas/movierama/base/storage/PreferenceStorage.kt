package com.andreolas.movierama.base.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.andreolas.movierama.base.storage.DataStorePreferenceStorage.PreferencesKeys.PREF_BLACK_BACKGROUNDS
import com.andreolas.movierama.base.storage.DataStorePreferenceStorage.PreferencesKeys.PREF_ENCRYPTED_SHARED_PREFS
import com.andreolas.movierama.base.storage.DataStorePreferenceStorage.PreferencesKeys.PREF_HAS_SESSION
import com.andreolas.movierama.base.storage.DataStorePreferenceStorage.PreferencesKeys.PREF_MATERIAL_YOU
import com.andreolas.movierama.base.storage.DataStorePreferenceStorage.PreferencesKeys.PREF_REQUEST_TOKEN
import com.andreolas.movierama.base.storage.DataStorePreferenceStorage.PreferencesKeys.PREF_SELECTED_THEME
import com.andreolas.movierama.ui.theme.Theme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

interface PreferenceStorage {
  suspend fun selectTheme(theme: String)
  val selectedTheme: Flow<String>

  suspend fun setMaterialYou(isEnabled: Boolean)
  val isMaterialYouEnabled: Flow<Boolean>

  suspend fun setBlackBackgrounds(isEnabled: Boolean)
  val isBlackBackgroundsEnabled: Flow<Boolean>

  suspend fun setEncryptedPreferences(value: String)
  val encryptedPreferences: Flow<String?>

  // TODO Remove from here probably
  suspend fun clearToken()
  suspend fun setToken(token: String)
  val token: Flow<String?>

  suspend fun setHasSession(hasSession: Boolean)
  val hasSession: Flow<Boolean>
}

@Singleton
class DataStorePreferenceStorage @Inject constructor(
  private val dataStore: DataStore<Preferences>,
) : PreferenceStorage {
  companion object {
    const val PREFS_NAME = "application_preferences"
  }

  object PreferencesKeys {
    val PREF_ENCRYPTED_SHARED_PREFS = stringPreferencesKey("encrypted.shared.prefs")
    val PREF_SELECTED_THEME = stringPreferencesKey("settings.theme")
    val PREF_MATERIAL_YOU = stringPreferencesKey("settings.material.you")
    val PREF_BLACK_BACKGROUNDS = stringPreferencesKey("settings.black.backgrounds")

    val PREF_REQUEST_TOKEN = stringPreferencesKey("request.token")
    val PREF_HAS_SESSION = booleanPreferencesKey("user.has.valid.session")
  }

  override suspend fun selectTheme(theme: String) {
    dataStore.edit {
      Timber.d("Set theme to $theme")
      it[PREF_SELECTED_THEME] = theme
    }
  }

  override val selectedTheme = dataStore.data.map {
    it[PREF_SELECTED_THEME] ?: Theme.SYSTEM.storageKey
  }

  override suspend fun setMaterialYou(isEnabled: Boolean) {
    dataStore.edit {
      it[PREF_MATERIAL_YOU] = isEnabled.toString()
    }
  }

  override val isMaterialYouEnabled = dataStore.data.map {
    it[PREF_MATERIAL_YOU]?.toBoolean() ?: false
  }

  override suspend fun setBlackBackgrounds(isEnabled: Boolean) {
    dataStore.edit {
      it[PREF_BLACK_BACKGROUNDS] = isEnabled.toString()
    }
  }

  override val isBlackBackgroundsEnabled: Flow<Boolean> = dataStore.data.map {
    it[PREF_BLACK_BACKGROUNDS]?.toBoolean() ?: false
  }

  override suspend fun setEncryptedPreferences(value: String) {
    dataStore.edit {
      it[PREF_ENCRYPTED_SHARED_PREFS] = value
    }
  }

  override val encryptedPreferences = dataStore.data.map {
    it[PREF_ENCRYPTED_SHARED_PREFS]
  }

  override suspend fun clearToken() {
    dataStore.edit {
      it.remove(PREF_REQUEST_TOKEN)
    }
  }

  override suspend fun setToken(token: String) {
    dataStore.edit {
      it[PREF_REQUEST_TOKEN] = token
    }
  }

  override val token = dataStore.data.map {
    val token = it[PREF_REQUEST_TOKEN]
    if (token.isNullOrEmpty()) {
      return@map null
    } else {
      token
    }
  }

  override suspend fun setHasSession(hasSession: Boolean) {
    dataStore.edit {
      it[PREF_HAS_SESSION] = hasSession
    }
  }

  override val hasSession: Flow<Boolean> = dataStore.data.map {
    it[PREF_HAS_SESSION] ?: false
  }
}
