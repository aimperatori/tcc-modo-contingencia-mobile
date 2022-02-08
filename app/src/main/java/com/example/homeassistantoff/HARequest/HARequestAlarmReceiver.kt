package com.example.homeassistantoff.HARequest

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import android.util.Log
import androidx.core.content.ContextCompat.getSystemService
import androidx.preference.PreferenceManager
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.homeassistantoff.Messaging.Notification
import com.example.homeassistantoff.utils.Constants
import com.example.homeassistantoff.utils.Constants.OFFLINE
import com.example.homeassistantoff.utils.Constants.ONLINE
import org.json.JSONObject


open class HARequestAlarmReceiver() : BroadcastReceiver() {

    override fun onReceive(context: Context?, p1: Intent?) {

        Log.d("Alarme", "acionou o alarme")

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val url = sharedPreferences.getString("urlHomeAssistant", "")
        val token = sharedPreferences.getString("longLiveToken", "")

        Log.d("Alarme", "URL SETADA: $url")
        Log.d("Alarme", "TOKEN SETADA: $token")

        val queue = Volley.newRequestQueue(context)

        val accessTokenRequest: JsonObjectRequest = object : JsonObjectRequest(
            Method.GET, url, JSONObject(),
            Response.Listener { response ->
                Log.d("Alarme", "Response: %s".format(response.toString()))

                val intent = Intent(ONLINE)
//                val extras = Bundle()
//                extras.putString("text", "on")
//                intent.putExtras(extras)
                context!!.sendBroadcast(intent)

            }, Response.ErrorListener { error ->
                Log.d("Alarme", "Error: %s".format(error.toString()))

                Notification.sendNotification(context!!, "Home assistant OFF", "Atenção sua intancia do home assistant está offline")

                val intent = Intent(OFFLINE)
//                val extras = Bundle()
//                extras.putString("text", "off")
//                intent.putExtras(extras)
                context!!.sendBroadcast(intent)

            }) {
                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    val params: MutableMap<String, String> = HashMap()
                    params["Authorization"] = "Bearer $token"
                    //..add other headers
                    return params
            }
        }

        queue.add(accessTokenRequest)
    }


    companion object {

        fun manageHARequests(context : Context)  {
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            val appRequestActive = sharedPreferences.getBoolean(Constants.APP_REQUEST_SETTING, false)

            Log.d("Requests", appRequestActive.toString())

            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, HARequestAlarmReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)

            if (appRequestActive) {
                Log.d("Alarme", "Habilitou alarme")

                alarmManager.setInexactRepeating(
                    AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    SystemClock.elapsedRealtime(),
                    (1000 * 5).toLong(), // each 5 sec
                    pendingIntent
                )
            }
            else {
                Log.d("Alarme", "Cancelou alarme")

                alarmManager.cancel(pendingIntent)
            }
        }
    }
}