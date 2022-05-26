package com.example.homeassistantoff.temperature

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.homeassistantoff.data.Response

class TemperatureViewModel(
    private val repository: TemperatureRepository = TemperatureRepository()
) : ViewModel() {

    fun getResponseUsingLiveData(curDate : String) : LiveData<Response> {
        return repository.getResponseFromRealtimeDatabaseUsingLiveData(curDate)
    }

}