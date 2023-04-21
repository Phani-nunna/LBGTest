package com.test.lbgcodetest.repository

import com.test.lbgcodetest.model.PopularMoviesList
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface RetrofitService {
    @GET("MostPopularMovies/k_3kfujbdw")
    suspend fun getAllPopularMovies(): PopularMoviesList

    companion object {
       private const val baseUrl:String = "https://imdb-api.com/en/API/"

        var retrofitService: RetrofitService? = null

        //Create the RetrofitService instance using the retrofit.
        fun getInstance(): RetrofitService {

            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }
    }
}