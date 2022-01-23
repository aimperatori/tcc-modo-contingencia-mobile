package com.example.homeassistantoff

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.google.firebase.ktx.Firebase
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.ktx.messaging
import android.content.Intent
import com.example.homeassistantoff.collectedData.CollectedDataActivity
import com.example.homeassistantoff.utils.Constants


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val logTokenBtn = findViewById<Button>(R.id.logTokenBtn)

        logTokenBtn.setOnClickListener {
            // Get token
            // [START log_reg_token]
            Firebase.messaging.getToken().addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                    return@OnCompleteListener
                }

                // Get new FCM registration token
                val token = task.result

                // Log and toast
                val msg = token.toString()
                Log.d(TAG, msg)
                Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
            })
            // [END log_reg_token]
        }


        val listBtn = findViewById<Button>(R.id.filesBtn)

//        listBtn.setOnClickListener {
//            val intent = Intent(this, ListActivity::class.java)
//            startActivity(intent)
//        }


        val collectedDataBtn = findViewById<Button>(R.id.collectedDataBtn)

        collectedDataBtn.setOnClickListener {
            val intent = Intent(this, CollectedDataActivity::class.java)
            startActivity(intent)
        }
    }

    companion object {

        private const val TAG = "MainActivity"
    }
}