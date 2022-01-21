package com.example.homeassistantoff.collectedFile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.homeassistantoff.data.FirebaseFilesCallback
import kotlinx.coroutines.Dispatchers

class CollectedFileViewModel (
        private val index : String,
        private val repository: CollectedFileRepository = CollectedFileRepository(index)
    ): ViewModel() {
        fun getResponseUsingCallback(callback: FirebaseFilesCallback) = repository.getResponseFromRealtimeDatabaseUsingCallback(callback)

        fun getResponseUsingLiveData() = repository.getResponseFromRealtimeDatabaseUsingLiveData()

        val responseLiveData = liveData(Dispatchers.IO) {
            emit(repository.getResponseFromRealtimeDatabaseUsingCoroutines())
        }
    }