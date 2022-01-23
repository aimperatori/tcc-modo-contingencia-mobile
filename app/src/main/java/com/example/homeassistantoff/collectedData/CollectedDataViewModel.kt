package com.example.homeassistantoff.collectedData

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.homeassistantoff.data.FirebaseCallback
import com.example.homeassistantoff.data.Response
import kotlinx.coroutines.Dispatchers

class CollectedDataViewModel (
    private val repository: CollectedDataRepository = CollectedDataRepository()
): ViewModel() {

    fun getResponseOnDataChange(callback: FirebaseCallback) {
        repository.getResponseOnDataChange(callback)
    }

    fun getResponseUsingCallback(callback: FirebaseCallback) {
        repository.getResponseFromRealtimeDatabaseUsingCallback(callback)
    }

    fun getResponseUsingLiveData() : LiveData<Response> {
        return repository.getResponseFromRealtimeDatabaseUsingLiveData()
    }

    val responseLiveData = liveData(Dispatchers.IO) {
        emit(repository.getResponseFromRealtimeDatabaseUsingCoroutines())
    }
}