package com.example.homeassistantoff.temperature

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.homeassistantoff.data.FirebaseCallback
import com.example.homeassistantoff.data.Response
import com.example.homeassistantoff.data.ResponseFiles

class TemperatureViewModel(
    private val repository: TemperatureRepository = TemperatureRepository()
) : ViewModel() {

    fun getResponseOnDataChange(callback: FirebaseCallback) {
        repository.getResponseOnDataChange(callback)
    }

//    fun getResponseUsingLiveData(curDate : String) : LiveData<Response> {
//        return repository.getResponseFromRealtimeDatabaseUsingLiveData(curDate)
//    }

}