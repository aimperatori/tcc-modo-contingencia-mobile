package com.example.homeassistantoff.rangedImages

import androidx.lifecycle.MutableLiveData
import com.example.homeassistantoff.data.*
import com.example.homeassistantoff.utils.Constants
import com.google.firebase.database.*

class RangedImagesRepository(
    private val rootRef: DatabaseReference = FirebaseDatabase.getInstance().reference,

) {

    fun getResponseFromRealtimeDatabaseUsingLiveData(curDate : String) : MutableLiveData<ResponseFiles> {
        val dataRef: Query = rootRef.child(Constants.CAMERA_REF).child(curDate)
        val mutableLiveData = MutableLiveData<ResponseFiles>()
        dataRef.get().addOnCompleteListener { task ->
            val response = ResponseFiles()
            if (task.isSuccessful) {
                val result = task.result
                result?.let {
                    response.camera = result.children.map { snapShot ->
                        snapShot.getValue(Camera::class.java)!!
                    }
                }
            } else {
                response.exception = task.exception
            }
            mutableLiveData.value = response
        }
        return mutableLiveData
    }
}