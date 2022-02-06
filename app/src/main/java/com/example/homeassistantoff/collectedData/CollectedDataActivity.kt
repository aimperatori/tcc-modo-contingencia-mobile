package com.example.homeassistantoff.collectedData

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.homeassistantoff.R
import com.example.homeassistantoff.data.FirebaseCallback
import com.example.homeassistantoff.data.Response
import com.example.homeassistantoff.utils.Constants.TAG
import com.example.homeassistantoff.utils.Helper
import com.example.homeassistantoff.utils.Constants.COLLECTED_DATA_SELECTED
import com.example.homeassistantoff.pager.ViewPagerActivity


class CollectedDataActivity : AppCompatActivity() {
    private lateinit var viewModel: CollectedDataViewModel
    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.listpage_collecteddata)

        viewModel = ViewModelProvider(this).get(CollectedDataViewModel::class.java)

        listView = findViewById(R.id.collectedData_listView)

        getResponseOnDataChange()
//        getResponseUsingCallback()
//        getResponseUsingLiveData()
//        getResponseUsingCoroutines()
    }

    private fun getResponseOnDataChange() {
        viewModel.getResponseOnDataChange(object : FirebaseCallback {
            override fun onResponse(response: Response) {
                renderUI(response)
            }
        })
    }

    private fun getResponseUsingCallback() {
        viewModel.getResponseUsingCallback(object : FirebaseCallback {
            override fun onResponse(response: Response) {
                renderUI(response)
            }
        })
    }

    private fun getResponseUsingLiveData() {
        viewModel.getResponseUsingLiveData().observe(this) {
            renderUI(it)
        }
    }

    private fun getResponseUsingCoroutines() {
        viewModel.responseLiveData.observe(this) {
            renderUI(it)
        }
    }

    private fun renderUI(response: Response) {

        listView.adapter = MyCustomAdapter(this, response)

        listView.setOnItemClickListener { _, _, position, _ ->
            val element = listView.adapter.getItemId(position) // The item that was clicked
            val intent = Intent(this, ViewPagerActivity::class.java)
            intent.putExtra(COLLECTED_DATA_SELECTED, response.collectedData?.get(element.toInt()))
            startActivity(intent)
        }

        response.exception?.let { exception ->
            exception.message?.let {
                Log.e(TAG, it)
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
            val rowList = LayoutInflater.from(mContext).inflate(R.layout.listpage_collecteddata_row, p2, false)

            // DESCENDING
            var index = mResponse.collectedData?.size!!-1 - p0
            val row = mResponse.collectedData?.get(index)

            // CreatedDateTime
            val positionTextView = rowList.findViewById<TextView>(R.id.createdDateTime)
            positionTextView.text = Helper.formatDateTime(row?.createdDateTime!!)

            // Temp
            val tempTextView = rowList.findViewById<TextView>(R.id.temp)
            tempTextView.text = row.temp.toString() + " ºC"

            // Humidity
            val humidityTextView = rowList.findViewById<TextView>(R.id.humidity)
            humidityTextView.text = row.humidity.toString() + " %"

            // Gas and Smoke
            val gasSmokeTextView = rowList.findViewById<TextView>(R.id.gas_smoke)
            gasSmokeTextView.text = row.gas_smoke.toString() + " "+ mContext.getText(R.string.ppm)

            // Movement
            val movementTextView = rowList.findViewById<TextView>(R.id.movement)
            movementTextView.text = Helper.booleanToNoYes(row.movement?.movDetected!!)

            return rowList
        }
    }
}