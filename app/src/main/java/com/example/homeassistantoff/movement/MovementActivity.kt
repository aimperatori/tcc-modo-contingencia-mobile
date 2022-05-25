package com.example.homeassistantoff.movement

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.homeassistantoff.R
import com.example.homeassistantoff.data.FirebaseCallback
import com.example.homeassistantoff.data.Response
import com.example.homeassistantoff.utils.Constants
import com.example.homeassistantoff.utils.Helper

class MovementActivity : AppCompatActivity() {

    private lateinit var viewModel: MovementViewModel
    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.listpage_collecteddata)

        viewModel = ViewModelProvider(this).get(MovementViewModel::class.java)

        listView = findViewById(R.id.collectedData_listView)

        getResponseOnDataChange()
    }

    private fun getResponseOnDataChange() {
        viewModel.getResponseOnDataChange(object : FirebaseCallback {
            override fun onResponse(response: Response) {
                renderUI(response)
            }
        })
    }

    private fun renderUI(response: Response) {

        listView.adapter = MyCustomAdapter(this, response)

        response.exception?.let { exception ->
            exception.message?.let {
                Log.e(Constants.TAG, it)
            }
        }
    }

    private class MyCustomAdapter(context : Context, response: Response) : BaseAdapter() {

        private val mContext : Context = context
        private val mResponse : Response = response

        // number of lines
        override fun getCount(): Int {
            return mResponse.collectedData?.size!!
        }

        override fun getItem(p0: Int): Any {
            return p0.toLong()
        }

        override fun getItemId(p0: Int): Long {
            // DESCENDING
            return mResponse.collectedData?.size!!-1 - p0.toLong()
        }

        // render each line
        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            val rowList = LayoutInflater.from(mContext).inflate(R.layout.listpage_collecteddata_movement, p2, false)

            // DESCENDING
            var index = mResponse.collectedData?.size!!-1 - p0
            val row = mResponse.collectedData?.get(index)

            val positionTextView = rowList.findViewById<TextView>(R.id.movement)
            positionTextView.text = Helper.formatDateTime(row?.created!!)

            return rowList
        }
    }
}