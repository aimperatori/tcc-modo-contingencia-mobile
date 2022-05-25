package com.example.homeassistantoff.gasSmoke

import androidx.lifecycle.ViewModel
import com.example.homeassistantoff.data.FirebaseCallback

class GasSmokeViewModel(
    private val repository: GasSmokeRepository = GasSmokeRepository()
) : ViewModel() {

    fun getResponseOnDataChange(callback: FirebaseCallback) {
        repository.getResponseOnDataChange(callback)
    }

//    fun getResponseUsingLiveData(curDate : String) : LiveData<Response> {
//        return repository.getResponseFromRealtimeDatabaseUsingLiveData(curDate)
//    }

}