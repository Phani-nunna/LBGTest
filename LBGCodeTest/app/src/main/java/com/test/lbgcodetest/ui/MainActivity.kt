package com.test.lbgcodetest.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.test.lbgcodetest.viewmodel.MyViewModelFactory
import com.test.lbgcodetest.repository.RetrofitService
import com.test.lbgcodetest.repository.MainRepository
import com.test.lbgcodetest.viewmodel.MainViewModel
import com.test.lbgcodetest.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private val retrofitService = RetrofitService.getInstance()
    //private val progressBar:ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val adapter = MainAdapter()
        val progressBar :ProgressBar
        ActivityMainBinding.inflate(layoutInflater).apply {
            setContentView(root)
            //set adapter in recyclerview
            recyclerview.adapter = adapter
            progressBar = this.progressBar
        }

        //get viewmodel instance using ViewModelProvider.Factory
        ViewModelProvider(this, MyViewModelFactory(MainRepository(retrofitService))).get(
            MainViewModel::class.java
        ).apply {
            //the observer will only receive events if the owner(activity) is in active state
            //invoked when movieList data changes
            movieList.observe(this@MainActivity) {
                Log.d(TAG, "movieList: $it")
                adapter.submitList(it)
            }
            //invoked when a network exception occurred
            errorMessage.observe(this@MainActivity) {
                Log.d(TAG, "errorMessage: $it")
            }
            loading.observe(this@MainActivity){
                progressBar.visibility= View.GONE
            }
            lifecycleScope.launch {
                getAllMovies()

            }

        }
    }
}