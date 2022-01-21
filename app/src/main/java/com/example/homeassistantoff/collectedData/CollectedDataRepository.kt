package com.example.homeassistantoff.collectedData

import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await
import com.example.homeassistantoff.data.CollectedData
import com.example.homeassistantoff.data.FirebaseCallback
import com.example.homeassistantoff.data.Response
import com.example.homeassistantoff.utils.Constants.COLLECTEDDATA_REF
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.flow

class CollectedDataRepository(
    private val rootRef: DatabaseReference = FirebaseDatabase.getInstance().reference,
    private val collectedDataRef: DatabaseReference = rootRef.child(COLLECTEDDATA_REF),
) {
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
