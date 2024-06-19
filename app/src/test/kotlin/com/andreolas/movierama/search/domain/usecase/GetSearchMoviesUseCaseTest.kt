package com.andreolas.movierama.search.domain.usecase

import com.andreolas.factories.MediaItemFactory
import com.andreolas.factories.MediaItemFactory.toWizard
import com.andreolas.movierama.MainDispatcherRule
import com.andreolas.movierama.fakes.repository.FakeMoviesRepository
import com.andreolas.movierama.home.domain.usecase.GetSearchMoviesUseCase
import com.andreolas.movierama.home.domain.usecase.SearchResult
import com.divinelink.core.model.media.MediaType
import com.divinelink.core.network.media.model.search.movie.SearchRequestApi
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetSearchMoviesUseCaseTest {

  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()
  private val testDispatcher = mainDispatcherRule.testDispatcher

  private lateinit var repository: FakeMoviesRepository

  private val request = SearchRequestApi(query = "test query", page = 1)

  // Movies with id 1, 3, 5, 7, 9 are marked as favorite.
  private val localFavoriteMovies = MediaItemFactory.MoviesList(1..10 step 2).map {
    it.toWizard { withFavorite(true) }
  }

  private val searchResult = MediaItemFactory.MoviesList()

  @Before
  fun setUp() {
    repository = FakeMoviesRepository()
  }

  @Test
  fun `given both favorites and non favorites when I fetch popular then I expect combined list`() =
    runTest {
      val expectedResult = Result.success(
        SearchResult(
          query = "test query",
          searchList = searchResult.mapIndexed { index, movie ->
            movie.copy(isFavorite = index % 2 == 0)
          }
        )
      )

      repository.mockFetchSearchMovies(
        request = request,
        response = Result.success(searchResult)
      )

      repository.mockFetchFavoriteMoviesIds(
        response = Result.success(
          localFavoriteMovies.map {
            Pair(it.id, MediaType.MOVIE)
          }
        )
      )

      val useCase = GetSearchMoviesUseCase(
        repository = repository.mock,
        dispatcher = testDispatcher,
      )
      val result = useCase(request).last()

      assertThat(result).isEqualTo(expectedResult)
    }

  @Test
  fun `given remote data failed then I expect Error Result`() = runTest {
    val expectedResult = Result.failure<Exception>(Exception("Something went wrong."))

    repository.mockFetchSearchMovies(
      request = request,
      response = Result.failure(Exception()),
    )
    val useCase = GetSearchMoviesUseCase(
      repository = repository.mock,
      dispatcher = testDispatcher,
    )
    val result = useCase(request).first()
    assertThat(result).isInstanceOf(expectedResult::class.java)
  }

  @Test
  fun `test error result`() = runTest {
    val expectedResult = Result.failure<Exception>(Exception("Something went wrong."))

    repository.mockFetchSearchMovies(
      request = request,
      response = Result.failure(Exception()),
    )

    val useCase = GetSearchMoviesUseCase(
      repository = repository.mock,
      dispatcher = testDispatcher,
    )
    val result = useCase(request).last()
    assertThat(result).isInstanceOf(expectedResult::class.java)
  }
}