package com.example.homeassistantoff.HARequest

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.homeassistantoff.utils.Constants.OFFLINE
import com.example.homeassistantoff.utils.Constants.ONLINE
import org.json.JSONObject


open class HARequestAlarmReceiver() : BroadcastReceiver() {

    override fun onReceive(context: Context?, p1: Intent?) {

        Log.d("Alarme", "acionou o alarme")

        val queue = Volley.newRequestQueue(context)

        val url = "http://10.0.0.111:8123/api/"
        val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiIwNjA3YTYzZThkMGE0MTM1ODAwNGRiYjI4ODNlNGQ5ZCIsImlhdCI6MTY0MzU2NTg0MiwiZXhwIjoxOTU4OTI1ODQyfQ.BF1c2dZyjOypgUBxF8p6Xq77FTFKTf38yeNdAkFL_Tc"

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
}