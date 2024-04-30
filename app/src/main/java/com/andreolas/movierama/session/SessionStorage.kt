package com.andreolas.movierama.session

import com.andreolas.movierama.base.storage.EncryptedStorage
import com.andreolas.movierama.base.storage.PreferenceStorage
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionStorage @Inject constructor(
  private val storage: PreferenceStorage,
  private val encryptedStorage: EncryptedStorage
) {

  suspend fun clearToken() {
    storage.clearToken()
  }

  suspend fun setToken(token: String) {
    storage.setToken(token)
  }

  suspend fun setSession(sessionId: String) {
    encryptedStorage.setSessionId(sessionId)
    storage.setHasSession(true)
    storage.clearToken()
  }

  suspend fun clearSession() {
    encryptedStorage.clearSession()
    storage.setHasSession(false)
    storage.clearToken()
  }
}
