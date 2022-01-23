package com.example.homeassistantoff.collectedFile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class CollectedFileViewModelFactory(private val mIndex: String) :
    ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return CollectedFileViewModel(mIndex) as T
        }
    }



//class MyViewModelFactory(private val mApplication: Application, private val mParam: String) :
//    ViewModelProvider.Factory {
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        return MyViewModel(mApplication, mParam) as T
//    }
//}
