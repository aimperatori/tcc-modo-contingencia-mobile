package com.example.homeassistantoff.rangedImages

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.homeassistantoff.data.ResponseFiles


class RangedImagesViewModel (
    private val repository: RangedImagesRepository = RangedImagesRepository()
): ViewModel() {

    fun getResponseUsingLiveData(curDate : String) : LiveData<ResponseFiles> {
        return repository.getResponseFromRealtimeDatabaseUsingLiveData(curDate)
    }

}