package com.andreolas.movierama.base.data.remote.movies.service

import com.andreolas.movierama.base.data.remote.movies.dto.popular.PopularRequestApi
import com.andreolas.movierama.base.data.remote.movies.dto.popular.PopularResponseApi
import com.andreolas.movierama.base.data.remote.movies.dto.search.SearchRequestApi
import com.andreolas.movierama.base.data.remote.movies.dto.search.SearchResponseApi
import kotlinx.coroutines.flow.Flow

interface MovieService {

    suspend fun fetchPopularMovies(
        request: PopularRequestApi,
    ): Flow<PopularResponseApi>

    suspend fun fetchSearchMovies(
        request: SearchRequestApi,
    ): Flow<SearchResponseApi>
}