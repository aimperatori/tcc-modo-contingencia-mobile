package com.example.homeassistantoff.temperature

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.homeassistantoff.data.*
import com.example.homeassistantoff.utils.Constants
import com.google.firebase.database.*

class TemperatureRepository(
    private val rootRef: DatabaseReference = FirebaseDatabase.getInstance().reference,
//    private val dataRef: Query = rootRef.child(Constants.COLLECTED_DATA_REF).child(Constants.TEMPERATURE_REF)
) {

//    fun getResponseOnDataChange(callback: FirebaseCallback) {
//        dataRef.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                val response = Response()
//                response.collectedData = dataSnapshot.children.map { snapShot ->
//                    snapShot.getValue(CollectedData::class.java)!!
//                }
//                callback.onResponse(response)
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Log.w("GasSmokeRepository", "loadPost:onCancelled", error.toException())
//            }
//        })
//    }

    fun getResponseFromRealtimeDatabaseUsingLiveData(curDate : String) : MutableLiveData<Response> {
        val dataRef: Query = rootRef.child(Constants.COLLECTED_DATA_REF).child(Constants.TEMPERATURE_REF).child(curDate)

        val mutableLiveData = MutableLiveData<Response>()
        dataRef.get().addOnCompleteListener { task ->
            val response = Response()
            if (task.isSuccessful) {
                val result = task.result
                result?.let {
                    response.collectedData = result.children.map { snapShot ->
                        snapShot.getValue(CollectedData::class.java)!!
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