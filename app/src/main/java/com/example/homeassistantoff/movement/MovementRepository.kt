package com.example.homeassistantoff.movement

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.homeassistantoff.data.CollectedData
import com.example.homeassistantoff.data.FirebaseCallback
import com.example.homeassistantoff.data.Response
import com.example.homeassistantoff.utils.Constants
import com.google.firebase.database.*

class MovementRepository(
    private val rootRef: DatabaseReference = FirebaseDatabase.getInstance().reference,
//    private val dataRef: Query = rootRef.child(Constants.COLLECTED_DATA_REF).child(Constants.MOVEMENT_REF).orderByChild(
//        Constants.CREATED_DATE_TIME
//    ),
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
//                Log.w("MovementRepository", "loadPost:onCancelled", error.toException())
//            }
//        })
//    }

    fun getResponseFromRealtimeDatabaseUsingLiveData(curDate : String) : MutableLiveData<Response> {
        val dataRef: Query = rootRef.child(Constants.COLLECTED_DATA_REF).child(Constants.MOVEMENT_REF).child(curDate)

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