package com.test.lbgcodetest.repository

import com.test.lbgcodetest.model.PopularMoviesList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


open class MainRepository constructor(
    private val retrofitService: RetrofitService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): MainRepositoryInterface {

    override suspend fun getAllMovies(): PopularMoviesList = withContext(dispatcher) {
        retrofitService.getAllPopularMovies()
    }
}

