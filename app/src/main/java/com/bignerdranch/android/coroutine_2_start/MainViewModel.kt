package com.bignerdranch.android.coroutine_2_start

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import kotlin.concurrent.thread

class MainViewModel:ViewModel() {

    private val exceptionHandler =
        CoroutineExceptionHandler { _, throwable ->
            Log.d("MainViewModel","Error $throwable")
        }
    private val parentJob = Job()
    private val coroutineScope = CoroutineScope(
        Dispatchers.Main+parentJob+exceptionHandler)


    fun method(){
       val job1 = coroutineScope.launch {
            delay(3000)
            Log.d("MainViewModel", "1 coroutine finish")


        }
        val job2 = coroutineScope.launch {
            delay(1000)
            Log.d("MainViewModel", "2 coroutine finish")
        }
        val job3 = coroutineScope.launch {
            delay(2000)

            launch {

                Log.d("MainViewModel", "3.1 coroutine finish")

                launch {
                    Log.d("MainViewModel", "3.2 coroutine finish")
                    error()
                }
            }

            Log.d("MainViewModel", "3 coroutine finish")
        }

    }
    private fun error(){
        throw RuntimeException("error")
    }
    override fun onCleared() {
        super.onCleared()
        coroutineScope.cancel()
    }
}