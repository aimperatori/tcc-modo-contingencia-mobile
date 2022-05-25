package com.example.homeassistantoff.humidity

import android.util.Log
import com.example.homeassistantoff.data.CollectedData
import com.example.homeassistantoff.data.FirebaseCallback
import com.example.homeassistantoff.data.Response
import com.example.homeassistantoff.utils.Constants
import com.google.firebase.database.*

class HumidityRepository(
    private val rootRef: DatabaseReference = FirebaseDatabase.getInstance().reference,
    private val dataRef: Query = rootRef.child(Constants.COLLECTED_DATA_REF).child(Constants.HUMIDITY_REF).orderByChild(
        Constants.CREATED_DATE_TIME
    ),
) {

    fun getResponseOnDataChange(callback: FirebaseCallback) {
        dataRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val response = Response()
                response.collectedData = dataSnapshot.children.map { snapShot ->
                    snapShot.getValue(CollectedData::class.java)!!
                }
                callback.onResponse(response)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("HumidityRepository", "loadPost:onCancelled", error.toException())
            }
        })
    }

}