package com.example.homeassistantoff.collectedData

import android.util.Log
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.tasks.await
import com.example.homeassistantoff.data.CollectedData
import com.example.homeassistantoff.data.FirebaseCallback
import com.example.homeassistantoff.data.Response
import com.example.homeassistantoff.utils.Constants.COLLECTED_DATA_REF
import com.example.homeassistantoff.utils.Constants.CREATED_DATE_TIME
import com.google.firebase.database.*

class CollectedDataRepository(
    private val rootRef: DatabaseReference = FirebaseDatabase.getInstance().reference,
    private val collectedDataRef: Query = rootRef.child(COLLECTED_DATA_REF).orderByChild(CREATED_DATE_TIME),
) {

    fun getResponseOnDataChange(callback: FirebaseCallback) {
        collectedDataRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val response = Response()
                response.collectedData = dataSnapshot.children.map { snapShot ->
                    snapShot.getValue(CollectedData::class.java)!!
                }
                callback.onResponse(response)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("RangedImagesRepository", "loadPost:onCancelled", error.toException())
            }
        })
    }

    fun getResponseFromRealtimeDatabaseUsingCallback(callback: FirebaseCallback) {
        collectedDataRef.get().addOnCompleteListener { task ->
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
            callback.onResponse(response)
        }
    }

    fun getResponseFromRealtimeDatabaseUsingLiveData() : MutableLiveData<Response> {
        val mutableLiveData = MutableLiveData<Response>()
        collectedDataRef.get().addOnCompleteListener { task ->
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

    suspend fun getResponseFromRealtimeDatabaseUsingCoroutines(): Response {
        val response = Response()
        try {
            response.collectedData = collectedDataRef.get().await().children.map { snapShot ->
                snapShot.getValue(CollectedData::class.java)!!
            }
        } catch (exception: Exception) {
            response.exception = exception
        }
        return response
    }
}