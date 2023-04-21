package com.test.lbgcodetest.repository

import com.test.lbgcodetest.model.PopularMoviesList

interface MainRepositoryInterface {
    suspend fun getAllMovies(): PopularMoviesList
}