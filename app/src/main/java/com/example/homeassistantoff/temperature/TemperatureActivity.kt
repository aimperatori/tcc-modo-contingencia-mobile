package com.example.homeassistantoff.temperature

import android.content.Context
import android.content.Intent
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
import com.example.homeassistantoff.pager.ViewPagerActivity
import com.example.homeassistantoff.utils.Constants
import com.example.homeassistantoff.utils.Helper
import java.text.SimpleDateFormat
import java.util.*

class TemperatureActivity : AppCompatActivity() {

    private lateinit var viewModel: TemperatureViewModel
    private lateinit var listView: ListView
    private val myCalendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.listpage_collecteddata)

        viewModel = ViewModelProvider(this).get(TemperatureViewModel::class.java)

        listView = findViewById(R.id.collectedData_listView)

        getResponseOnDataChange()
        //getResponseUsingLiveData()
    }

    private fun getResponseOnDataChange() {
        viewModel.getResponseOnDataChange(object : FirebaseCallback {
            override fun onResponse(response: Response) {
                renderUI(response)
            }
        })
    }

//    private fun getResponseUsingLiveData() {
//        val myFormat = "yyyy-MM-dd"
//        val dateFormat = SimpleDateFormat(myFormat)
//        val dateString = dateFormat.format(myCalendar.time)
//
//        viewModel.getResponseUsingLiveData(dateString).observe(this) {
//            renderUI(it)
//        }
//    }

    private fun renderUI(response: Response) {

        listView.adapter = MyCustomAdapter(this, response)

        listView.setOnItemClickListener { _, _, position, _ ->
            val element = listView.adapter.getItemId(position) // The item that was clicked
            val intent = Intent(this, ViewPagerActivity::class.java)
            intent.putExtra(Constants.COLLECTED_DATA_SELECTED, response.collectedData?.get(element.toInt()))
            startActivity(intent)
        }

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
            val rowList = LayoutInflater.from(mContext).inflate(R.layout.listpage_collecteddata_temperature, p2, false)

            // DESCENDING
            var index = mResponse.collectedData?.size!!-1 - p0
            val row = mResponse.collectedData?.get(index)

            // CreatedDateTime
            val createdTextView = rowList.findViewById<TextView>(R.id.temperatureCreated)
            createdTextView.text = Helper.formatDateTime(row?.created!!)

            // Temp
            val tempTextView = rowList.findViewById<TextView>(R.id.temperature)
            tempTextView.text = "${row.value} ºC"

            return rowList
        }
    }

}