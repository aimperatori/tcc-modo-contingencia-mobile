package com.example.homeassistantoff.rangedImages

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context
import android.content.Intent
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
import com.example.homeassistantoff.data.ResponseFiles
import com.example.homeassistantoff.pager.ViewPagerActivity
import com.example.homeassistantoff.utils.Constants.CAMERA_SELECTED
import com.example.homeassistantoff.utils.Constants.TAG
import java.text.SimpleDateFormat
import java.util.*


class RangedImagesActivity : AppCompatActivity() {
    private lateinit var viewModel: RangedImagesViewModel
    private lateinit var listView: ListView
    private val myCalendar = Calendar.getInstance()
    private lateinit var editText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.listpage_camera)

        viewModel = ViewModelProvider(this).get(RangedImagesViewModel::class.java)

        listView = findViewById(R.id.camera_listView)

        editText = findViewById(R.id.filterDateTxt)

        val date =
            OnDateSetListener { _, year, month, day ->
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

    private fun renderUI(response: ResponseFiles) {

        listView.adapter = MyCustomAdapter(this, response)

        listView.setOnItemClickListener { _, _, position, _ ->
            val element = listView.adapter.getItemId(position) // The item that was clicked
            val intent = Intent(this, ViewPagerActivity::class.java)
            intent.putExtra(CAMERA_SELECTED, response.camera?.get(element.toInt()))
            startActivity(intent)
        }

        response.exception?.let { exception ->
            exception.message?.let {
                Log.e(TAG, it)
            }
        }
    }

    private class MyCustomAdapter(context : Context, response: ResponseFiles) : BaseAdapter() {

        private val mContext : Context = context
        private val mResponse : ResponseFiles = response

        // number of lines
        override fun getCount(): Int {
            return mResponse.camera?.size!!
        }

        override fun getItem(p0: Int): Any {
            return p0.toLong()
        }

        override fun getItemId(p0: Int): Long {
            // DESCENDING
            return mResponse.camera?.size!!-1 - p0.toLong()
        }

        // render each line
        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            val rowList = LayoutInflater.from(mContext).inflate(R.layout.listpage_camera_ranged_images, p2, false)

            // DESCENDING
            var index = mResponse.camera?.size!!-1 - p0
            val row = mResponse.camera?.get(index)

            // From
            val fromTextView = rowList.findViewById<TextView>(R.id.imageTimeFrom)
            fromTextView.text = row?.from!!

            // From
            val toTextView = rowList.findViewById<TextView>(R.id.imageTimeTo)
            toTextView.text = row?.to!!

            return rowList
        }
    }
}