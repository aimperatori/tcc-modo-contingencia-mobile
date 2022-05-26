package com.example.homeassistantoff.movement

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.homeassistantoff.data.FirebaseCallback
import com.example.homeassistantoff.data.Response

class MovementViewModel(
    private val repository: MovementRepository = MovementRepository()
) : ViewModel() {

//    fun getResponseOnDataChange(callback: FirebaseCallback) {
//        repository.getResponseOnDataChange(callback)
//    }

    fun getResponseUsingLiveData(curDate : String) : LiveData<Response> {
        return repository.getResponseFromRealtimeDatabaseUsingLiveData(curDate)
    }

}