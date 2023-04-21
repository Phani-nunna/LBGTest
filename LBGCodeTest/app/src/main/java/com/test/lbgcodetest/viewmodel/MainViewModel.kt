package com.test.lbgcodetest.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.lbgcodetest.model.Movie
import com.test.lbgcodetest.repository.MainRepositoryInterface
import kotlinx.coroutines.launch

class MainViewModel(private val repository: MainRepositoryInterface) : ViewModel() {

    val movieList = MutableLiveData<List<Movie>>()

    val errorMessage = MutableLiveData<String>()
    val loading = MutableLiveData<Boolean>()

    fun getAllMovies() {
        viewModelScope.launch {
            try {
                movieList.postValue(repository.getAllMovies().items)
                loading.postValue(false)
              //  movieList.value = repository.getAllMovies().items
            } catch(e: Exception) {
                repository.getAllMovies().errorMessage
                errorMessage.postValue(e.message)
                loading.postValue(false)
            }
        }
    }
}