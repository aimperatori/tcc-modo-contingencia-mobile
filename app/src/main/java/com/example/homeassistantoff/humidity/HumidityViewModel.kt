package com.example.homeassistantoff.humidity

import androidx.lifecycle.ViewModel
import com.example.homeassistantoff.data.FirebaseCallback

class HumidityViewModel(
    private val repository: HumidityRepository = HumidityRepository()
) : ViewModel() {

    fun getResponseOnDataChange(callback: FirebaseCallback) {
        repository.getResponseOnDataChange(callback)
    }

}