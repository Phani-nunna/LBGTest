package com.test.lbgcodetest.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.test.lbgcodetest.MainCoroutineRule
import com.test.lbgcodetest.model.Movie
import com.test.lbgcodetest.model.PopularMoviesList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainRepositoryTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    /*// Sets the main coroutines dispatcher to a TestCoroutineScope for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()*/
    @Mock
    lateinit var retrofitService: RetrofitService
    lateinit var mainRepository: MainRepository
    @Mock
    lateinit var repository: MainRepositoryInterface

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        mainRepository = MainRepository(retrofitService)
    }



    @Test
    fun getAllMovies() {
        runBlocking {
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
            mainRepository.getAllMovies()
            assertEquals(repository.getAllMovies().items, expected)
        }
    }
}