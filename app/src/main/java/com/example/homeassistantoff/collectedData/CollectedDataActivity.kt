package com.example.homeassistantoff.collectedData

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.homeassistantoff.List.ListActivity
import com.example.homeassistantoff.R
import com.example.homeassistantoff.data.FirebaseCallback
import com.example.homeassistantoff.data.Response
import com.example.homeassistantoff.utils.Constants.TAG


class CollectedDataActivity : AppCompatActivity() {
    private lateinit var viewModel: CollectedDataViewModel
    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collecteddata)
        viewModel = ViewModelProvider(this).get(CollectedDataViewModel::class.java)
//        getResponseUsingCallback()
        getResponseUsingLiveData()
//        getResponseUsingCoroutines()

        listView = findViewById<ListView>(R.id.collectedData_listView)
    }

    private fun getResponseUsingCallback() {
        viewModel.getResponseUsingCallback(object : FirebaseCallback {
            override fun onResponse(response: Response) {
                print(response)
            }
        })
    }

    private fun getResponseUsingLiveData() {
        viewModel.getResponseUsingLiveData().observe(this, {
            print(it)
        })
    }

    private fun getResponseUsingCoroutines() {
        viewModel.responseLiveData.observe(this, {
            print(it)
        })
    }

    private fun print(response: Response) {

        listView.adapter = MyCustomAdapter(this, response)

//        response.collectedData?.let { collectedData ->
//            collectedData.forEach{ row ->
//                row.temp?.let {
//                    //Log.i(TAG, it)
//                    Toast.makeText(baseContext, it.toString(), Toast.LENGTH_LONG).show()
//                }
//            }
//        }

        response.exception?.let { exception ->
            exception.message?.let {
                Log.e(TAG, it)
            }
        }
    }

    private class MyCustomAdapter(context : Context, response: Response) : BaseAdapter() {

        private val mContext : Context = context
        private val mResponse : Response = response

        // numero de linhas
        override fun getCount(): Int {
            return mResponse.collectedData?.size!!
        }

        override fun getItem(p0: Int): Any {
            return "TESTE"
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        // renderiza cada linha
        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {

            val layoutInflater = LayoutInflater.from(mContext)

            val collectedData = mResponse.collectedData

            val rowList = layoutInflater.inflate(R.layout.row_listview, p2, false)

            val row = collectedData?.get(p0)

            val positionTextView = rowList.findViewById<TextView>(R.id.createdDateTime)
            positionTextView.text = row?.createdDateTime

            // NAMES
            val nameTextView = rowList.findViewById<TextView>(R.id.temp)
            nameTextView.text = row?.temp.toString()

            return rowList
        }
    }
}