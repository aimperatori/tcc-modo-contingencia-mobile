package com.example.homeassistantoff.humidity

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.homeassistantoff.data.Response

class HumidityViewModel(
    private val repository: HumidityRepository = HumidityRepository()
) : ViewModel() {

    fun getResponseUsingLiveData(curDate : String) : LiveData<Response> {
        return repository.getResponseFromRealtimeDatabaseUsingLiveData(curDate)
    }

}