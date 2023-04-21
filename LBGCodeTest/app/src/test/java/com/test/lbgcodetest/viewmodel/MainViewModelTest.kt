package com.test.lbgcodetest.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.test.lbgcodetest.MainCoroutineRule
import com.test.lbgcodetest.model.Movie
import com.test.lbgcodetest.model.PopularMoviesList
import com.test.lbgcodetest.repository.MainRepository
import com.test.lbgcodetest.repository.MainRepositoryInterface
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response
import java.lang.NullPointerException
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    // Sets the main coroutines dispatcher to a TestCoroutineScope for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: MainViewModel

    @Mock
    lateinit var repository: MainRepositoryInterface

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = MainViewModel(repository)
    }


    /* Copyright 2019 Google LLC.
       SPDX-License-Identifier: Apache-2.0 */
    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    fun <T> LiveData<T>.getOrAwaitValue(
        time: Long = 2,
        timeUnit: TimeUnit = TimeUnit.SECONDS
    ): T {
        var data: T? = null
        val latch = CountDownLatch(1)
        val observer = object : Observer<T> {
            override fun onChanged(o: T?) {
                data = o
                latch.countDown()
                this@getOrAwaitValue.removeObserver(this)
            }
        }

        this.observeForever(observer)

        // Don't wait indefinitely if the LiveData is not set.
        if (!latch.await(time, timeUnit)) {
            throw TimeoutException("LiveData value was never set.")
        }

        @Suppress("UNCHECKED_CAST")
        return data as T
    }

    @Test
    fun emptyMovieListTest() = runTest {
        val expected = emptyList<Movie>()
        val popularMoviesList = PopularMoviesList(items = expected, errorMessage = "")
        `when`(repository.getAllMovies()).thenReturn(popularMoviesList)
        viewModel.getAllMovies()
        advanceUntilIdle()
        assertEquals(viewModel.movieList.getOrAwaitValue(), expected)
    }

    @Test
    fun getAllMoviesTest() = runTest {
        var movie = Movie(
            "Greta Gerwig (dir.), Ariana Greenblatt, Margot Robbie",
            "Barbie (2023)",
            "tt1517268",
            "",
            "0",
            "https://m.media-amazon.com/images/M/MV5BOWIwZGY0OTYtZjUzYy00NzRmLTg5YzgtYWMzNWQ0MmZiY2MwXkEyXkFqcGdeQXVyMTUzMTg2ODkz._V1_UX128_CR0,12,128,176_AL_.jpg",
            "4",
            "-2",
            "Barbie",
            "2023"
        )
        val expected = listOf(movie)
        val popularMoviesList = PopularMoviesList(items = expected, errorMessage = "")
        `when`(repository.getAllMovies()).thenReturn(popularMoviesList)
        viewModel.getAllMovies()
        advanceUntilIdle()
        assertEquals(viewModel.movieList.getOrAwaitValue(), expected)

    }
}



  /*  @Test
    fun doTestSuccessCase() = runTest {
        val expected = emptyList<Movie>()
        val popularMoviesList = PopularMoviesList(items = expected, errorMessage = "")
        `when`(repository.getAllMovies()).thenReturn(popularMoviesList)
        viewModel.getAllMovies()
        advanceUntilIdle()
        assertEquals(viewModel.movieList.getOrAwaitValue(), expected)
    }*/

    /*@Test
    fun doTestFailureCase() = runTest(dispatchTimeoutMs = 10000) {
        launch(start = CoroutineStart.LAZY) {
            val expected = emptyList<Movie>()
            val popularMoviesList = PopularMoviesList(items = expected, errorMessage = "")
            `when`(repository.getAllMovies()).thenReturn(popularMoviesList)
            viewModel.getAllMovies()
             advanceUntilIdle()
           // delay(100000)
            assertEquals(viewModel.movieList.getOrAwaitValue(), expected)
        }
    }*/
