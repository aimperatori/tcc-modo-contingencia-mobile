package com.example.homeassistantoff.gasSmoke

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.homeassistantoff.R
import com.example.homeassistantoff.data.Response
import com.example.homeassistantoff.utils.Constants
import java.text.SimpleDateFormat
import java.util.*

class GasSmokeActivity : AppCompatActivity() {

    private lateinit var viewModel: GasSmokeViewModel
    private lateinit var listView: ListView
    private val myCalendar = Calendar.getInstance()
    private lateinit var editText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.listpage_collecteddata)

        viewModel = ViewModelProvider(this).get(GasSmokeViewModel::class.java)

        listView = findViewById(R.id.collectedData_listView)

        editText = findViewById(R.id.collectedDataFilterDateTxt)

        val date =
            DatePickerDialog.OnDateSetListener { _, year, month, day ->
                myCalendar[Calendar.YEAR] = year
                myCalendar[Calendar.MONTH] = month
                myCalendar[Calendar.DAY_OF_MONTH] = day
                updateLabel()
            }
        editText.setOnClickListener {
            DatePickerDialog(
                this,
                date,
                myCalendar[Calendar.YEAR],
                myCalendar[Calendar.MONTH],
                myCalendar[Calendar.DAY_OF_MONTH]
            ).show()
        }

        updateLabel()
    }

    private fun updateLabel() {
        val myFormat = "dd/MM/yyyy"
        val dateFormat = SimpleDateFormat(myFormat)

        val dateString = dateFormat.format(myCalendar.time)
        editText.setText(dateString)

        getResponseUsingLiveData()
    }


    private fun getResponseUsingLiveData() {
        val myFormat = "yyyy-MM-dd"
        val dateFormat = SimpleDateFormat(myFormat)
        val dateString = dateFormat.format(myCalendar.time)

        viewModel.getResponseUsingLiveData(dateString).observe(this) {
            renderUI(it)
        }
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
            val rowList = LayoutInflater.from(mContext).inflate(R.layout.listpage_collecteddata_gassmoke, p2, false)

            // DESCENDING
            var index = mResponse.collectedData?.size!!-1 - p0
            val row = mResponse.collectedData?.get(index)

            // CreatedDateTime
            val createdTextView = rowList.findViewById<TextView>(R.id.gasSmokeCreated)
            createdTextView.text = row?.created

            // PPM
            val tempTextView = rowList.findViewById<TextView>(R.id.gasSmoke)
            tempTextView.text = "${row?.value} ppm"

            return rowList
        }
    }
}