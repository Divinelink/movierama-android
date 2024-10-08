package com.divinelink.core.data.session.repository

import com.divinelink.core.commons.domain.data
import com.divinelink.core.data.session.mapper.map
import com.divinelink.core.model.account.AccountDetails
import com.divinelink.core.model.session.RequestToken
import com.divinelink.core.model.session.Session
import com.divinelink.core.network.session.model.CreateSessionRequestApi
import com.divinelink.core.network.session.service.SessionService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class ProdSessionRepository(private val remote: SessionService) : SessionRepository {

  override suspend fun createRequestToken(): Result<RequestToken> {
    val response = remote.createRequestToken()

    return Result.success(response.data.map())
  }

  override suspend fun createSession(token: CreateSessionRequestApi): Result<Session> {
    val response = remote.createSession(token)

    return Result.success(response.data.map())
  }

  override suspend fun deleteSession(sessionId: String): Result<Boolean> {
    val response = remote.deleteSession(sessionId = sessionId)

    return Result.success(response.data.success)
  }

  override fun getAccountDetails(sessionId: String): Flow<Result<AccountDetails>> = remote
    .getAccountDetails(sessionId)
    .map { apiResponse ->
      Result.success(apiResponse.map())
    }
    .catch { exception ->
      Result.failure<Exception>(Exception(exception.message))
    }
}
