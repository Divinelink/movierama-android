package com.andreolas.movierama.popular.domain.usecase

import com.andreolas.movierama.MainDispatcherRule
import com.andreolas.movierama.fakes.repository.FakeMoviesRepository
import com.andreolas.movierama.home.domain.model.PopularMovie
import com.andreolas.movierama.home.domain.usecase.RemoveFavoriteUseCase
import com.google.common.truth.Truth.assertThat
import gr.divinelink.core.util.domain.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RemoveFavoriteUseCaseTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()
    private val testDispatcher = mainDispatcherRule.testDispatcher

    private lateinit var repository: FakeMoviesRepository

    @Before
    fun setUp() {
        repository = FakeMoviesRepository()
    }

    private val remoteMovies = (1..6).map { index ->
        PopularMovie(
            id = index.toLong(),
            posterPath = "",
            releaseDate = "2000",
            title = "Fight Club $index",
            isFavorite = false,
            rating = index.toString(),
        )
    }.toMutableList()

    @Test
    fun `given movies is successfully saved I expect Success Result`() = runTest {
        val expectedResult = Result.Success(Unit)

        repository.mockRemoveFavorite(
            id = remoteMovies[2].id,
            response = Result.Success(Unit)
        )

        val useCase = RemoveFavoriteUseCase(
            repository = repository.mock,
            dispatcher = testDispatcher,
        )

        val result = useCase(remoteMovies[2].id)
        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun `given movie failed to save I expect Error Result`() = runTest {
        val expectedResult = Result.Error(Exception("Failed to mark as favorite."))

        repository.mockRemoveFavorite(
            id = remoteMovies[2].id,
            response = Result.Error(Exception())
        )

        val useCase = RemoveFavoriteUseCase(
            repository = repository.mock,
            dispatcher = testDispatcher,
        )

        val result = useCase(remoteMovies[2].id)
        assertThat(result).isInstanceOf(expectedResult::class.java)
    }

    @Test
    fun `given loading result then I expect general exception`() = runTest {
        val expectedResult = Result.Error(IllegalStateException())

        repository.mockRemoveFavorite(
            id = remoteMovies[2].id,
            response = Result.Loading
        )

        val useCase = RemoveFavoriteUseCase(
            repository = repository.mock,
            dispatcher = testDispatcher,
        )

        val result = useCase(remoteMovies[2].id)
        assertThat(result).isInstanceOf(expectedResult::class.java)
    }
}