package com.example.homeassistantoff.gasSmoke

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.homeassistantoff.data.FirebaseCallback
import com.example.homeassistantoff.data.Response

class GasSmokeViewModel(
    private val repository: GasSmokeRepository = GasSmokeRepository()
) : ViewModel() {

    fun getResponseUsingLiveData(curDate : String) : LiveData<Response> {
        return repository.getResponseFromRealtimeDatabaseUsingLiveData(curDate)
    }

}