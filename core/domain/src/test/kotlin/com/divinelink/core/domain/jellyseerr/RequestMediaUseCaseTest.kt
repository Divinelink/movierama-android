package com.divinelink.core.domain.jellyseerr

import com.divinelink.core.data.jellyseerr.model.JellyseerrRequestParams
import com.divinelink.core.datastore.PreferenceStorage
import com.divinelink.core.model.jellyseerr.request.JellyseerrMediaRequest
import com.divinelink.core.model.media.MediaType
import com.divinelink.core.testing.MainDispatcherRule
import com.divinelink.core.testing.repository.TestJellyseerrRepository
import com.divinelink.core.testing.storage.FakePreferenceStorage
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import kotlin.test.Test

class RequestMediaUseCaseTest {

  private lateinit var preferenceStorage: PreferenceStorage

  private val repository = TestJellyseerrRepository()

  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()
  private val testDispatcher = mainDispatcherRule.testDispatcher

  @Test
  fun `test requestMedia with null address throws exception`() = runTest {
    preferenceStorage = FakePreferenceStorage()

    val useCase = RequestMediaUseCase(
      repository = repository.mock,
      storage = preferenceStorage,
      dispatcher = testDispatcher,
    )

    val params = JellyseerrRequestParams(
      mediaType = MediaType.MOVIE.name,
      mediaId = 123,
      is4k = false,
      seasons = emptyList(),
    )

    useCase.invoke(params).collect {
      assertThat(it.isFailure).isTrue()
      assertThat(it.exceptionOrNull()).isInstanceOf(IllegalArgumentException::class.java)
      assertThat(it.exceptionOrNull()?.message).isEqualTo("Address cannot be null")
    }
  }

  @Test
  fun `test requestMedia with valid address`() = runTest {
    preferenceStorage = FakePreferenceStorage(jellyseerrAddress = "http://localhost:8096")

    repository.mockRequestMedia(Result.success(JellyseerrMediaRequest("Success")))

    val useCase = RequestMediaUseCase(
      repository = repository.mock,
      storage = preferenceStorage,
      dispatcher = testDispatcher,
    )

    val params = JellyseerrRequestParams(
      mediaType = MediaType.MOVIE.name,
      mediaId = 123,
      is4k = false,
      seasons = emptyList(),
    )

    useCase.invoke(params).collect {
      assertThat(it.isSuccess).isTrue()
      assertThat(it.getOrNull()).isEqualTo(JellyseerrMediaRequest("Success"))
    }
  }

  @Test
  fun `test requestMedia with failure`() = runTest {
    preferenceStorage = FakePreferenceStorage(jellyseerrAddress = "http://localhost:8096")

    repository.mockRequestMedia(Result.failure(IllegalArgumentException("Something went wrong")))

    val useCase = RequestMediaUseCase(
      repository = repository.mock,
      storage = preferenceStorage,
      dispatcher = testDispatcher,
    )

    val params = JellyseerrRequestParams(
      mediaType = MediaType.MOVIE.name,
      mediaId = 123,
      is4k = false,
      seasons = emptyList(),
    )

    useCase.invoke(params).collect {
      assertThat(it.isFailure).isTrue()
      assertThat(it.exceptionOrNull()).isInstanceOf(IllegalArgumentException::class.java)
      assertThat(it.exceptionOrNull()?.message).isEqualTo("Something went wrong")
    }
  }
}
