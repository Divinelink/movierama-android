package com.andreolas.movierama.base.data.remote.movies.service

import com.andreolas.movierama.base.communication.ApiConstants
import com.andreolas.movierama.base.communication.RestClient
import com.andreolas.movierama.base.data.remote.movies.dto.popular.PopularRequestApi
import com.andreolas.movierama.base.data.remote.movies.dto.popular.PopularResponseApi
import com.andreolas.movierama.base.data.remote.movies.dto.search.SearchRequestApi
import com.andreolas.movierama.base.data.remote.movies.dto.search.SearchResponseApi
import com.andreolas.movierama.base.storage.EncryptedPreferenceStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class ProdMovieService @Inject constructor(
    private val restClient: RestClient,
    encryptedStorage: EncryptedPreferenceStorage,
) : MovieService {
    private val apiKey = encryptedStorage.tmdbApiKey

    override suspend fun fetchPopularMovies(request: PopularRequestApi): Flow<PopularResponseApi> {
        val baseUrl = "${ApiConstants.TMDB_URL}/movie/popular?"
        val url = baseUrl + "api_key=$apiKey&language=en-US&page=${request.page}"

        val response = restClient.get<PopularResponseApi>(
            url = url,
        )

        return flowOf(response)
    }

    override suspend fun fetchSearchMovies(request: SearchRequestApi): Flow<SearchResponseApi> {
        val baseUrl = "${ApiConstants.TMDB_URL}/search/movie?"
        val url = baseUrl + "api_key=$apiKey" +
            "&language=en-US" +
            "&query=${request.query}" +
            "&page=${request.page}" +
            "&include_adult=false"

        val response = restClient.get<SearchResponseApi>(
            url = url,
        )

        return flowOf(response)
    }
}