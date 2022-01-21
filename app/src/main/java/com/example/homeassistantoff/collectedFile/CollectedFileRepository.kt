package com.example.homeassistantoff.collectedFile

import androidx.lifecycle.MutableLiveData
import com.example.homeassistantoff.data.*
import com.example.homeassistantoff.utils.Constants.COLLECTEDDATA_REF
import com.example.homeassistantoff.utils.Constants.FILES_REF
import com.example.homeassistantoff.utils.Constants.MOVEMENT_REF
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class CollectedFileRepository(
    private val index : String,
    private val rootRef: DatabaseReference = FirebaseDatabase.getInstance().reference,
    private val filesRef: DatabaseReference = rootRef.child(COLLECTEDDATA_REF).child(index).child(MOVEMENT_REF).child(FILES_REF),
) {
    fun getResponseFromRealtimeDatabaseUsingCallback(callback: FirebaseFilesCallback) {
        filesRef.get().addOnCompleteListener { task ->
            val response = ResponseFiles()
            if (task.isSuccessful) {
                val result = task.result
                result?.let {
                    response.files = result.children.map { snapShot ->
                        snapShot.getValue(Files::class.java)!!
                    }
                }
            } else {
                response.exception = task.exception
            }
            callback.onResponse(response)
        }
    }

    fun getResponseFromRealtimeDatabaseUsingLiveData() : MutableLiveData<ResponseFiles> {
        val mutableLiveData = MutableLiveData<ResponseFiles>()
        filesRef.get().addOnCompleteListener { task ->
            val response = ResponseFiles()
            if (task.isSuccessful) {
                val result = task.result
                result?.let {
                    response.files = result.children.map { snapShot ->
                        snapShot.getValue(Files::class.java)!!
                    }
                }
            } else {
                response.exception = task.exception
            }
            mutableLiveData.value = response
        }
        return mutableLiveData
    }

    suspend fun getResponseFromRealtimeDatabaseUsingCoroutines(): ResponseFiles {
        val response = ResponseFiles()
        try {
            response.files = filesRef.get().await().children.map { snapShot ->
                snapShot.getValue(Files::class.java)!!
            }
        } catch (exception: Exception) {
            response.exception = exception
        }
        return response
    }





//    fun getResponseFromFirestoreUsingCallback(callback: FirebaseStorageCallback) {
//        filesRef.get().addOnCompleteListener { task ->
//            val response = ResponseFiles()
//            if (task.isSuccessful) {
//                val result = task.result
//                result?.let {
//                    response.files = result.documents.mapNotNull { snapShot ->
//                        snapShot.toObject(Files::class.java)
//                    }
//                }
//            } else {
//                response.exception = task.exception
//            }
//            callback.onResponse(response)
//        }
//    }
//
//    fun getResponseFromFirestoreUsingLiveData() : MutableLiveData<ResponseFiles> {
//        val mutableLiveData = MutableLiveData<ResponseFiles>()
//        filesRef.get().addOnCompleteListener { task ->
//            val response = ResponseFiles()
//            if (task.isSuccessful) {
//                val result = task.result
//                result?.let {
//                    response.files = result.documents.mapNotNull { snapShot ->
//                        snapShot.toObject(Files::class.java)
//                    }
//                }
//            } else {
//                response.exception = task.exception
//            }
//            mutableLiveData.value = response
//        }
//        return mutableLiveData
//    }
//
//    suspend fun getResponseFromFirestoreUsingCoroutines(): ResponseFiles {
//        val response = ResponseFiles()
//        try {
//            response.files = filesRef.get().await().documents.mapNotNull { snapShot ->
//                snapShot.toObject(Files::class.java)
//            }
//        } catch (exception: Exception) {
//            response.exception = exception
//        }
//        return response
//    }
//
//    fun getResponseFromFirestoreUsingFlow() = flow {
//        val response = ResponseFiles()
//        try {
//            response.files =
//                filesRef.get().await().documents.mapNotNull { snapShot ->
//                    snapShot.toObject(Files::class.java)
//                }
//        } catch (exception: Exception) {
//            response.exception = exception
//        }
//        emit(response)
//    }
}