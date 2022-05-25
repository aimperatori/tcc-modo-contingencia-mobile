package com.example.homeassistantoff.movement

import androidx.lifecycle.ViewModel
import com.example.homeassistantoff.data.FirebaseCallback

class MovementViewModel(
    private val repository: MovementRepository = MovementRepository()
) : ViewModel() {

    fun getResponseOnDataChange(callback: FirebaseCallback) {
        repository.getResponseOnDataChange(callback)
    }

}