package com.example.homeassistantoff.collectedData

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProvider
import com.example.homeassistantoff.R
import com.example.homeassistantoff.data.FirebaseCallback
import com.example.homeassistantoff.data.Response
import com.example.homeassistantoff.utils.Constants.TAG
import com.example.homeassistantoff.utils.Helper
import java.net.URI
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import android.graphics.BitmapFactory

import android.graphics.Bitmap
import android.os.NetworkOnMainThreadException
import com.bumptech.glide.Glide
import com.example.homeassistantoff.collectedFile.CollectedFileActivity
import com.example.homeassistantoff.utils.Constants
import com.example.homeassistantoff.utils.Constants.COLLECTEDDATA_SELECTED
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import java.io.InputStream
import java.net.MalformedURLException
import java.net.URL


class CollectedDataActivity : AppCompatActivity() {
    private lateinit var viewModel: CollectedDataViewModel
    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collecteddata)
        viewModel = ViewModelProvider(this).get(CollectedDataViewModel::class.java)

        getResponseUsingCallback()
//        getResponseUsingLiveData()
//        getResponseUsingCoroutines()


        listView = findViewById<ListView>(R.id.collectedData_listView)
    }

    private fun getResponseUsingCallback() {
        viewModel.getResponseUsingCallback(object : FirebaseCallback {
            override fun onResponse(response: Response) {
                print(response)
            }
        })
//        viewModel.getResponseFromFirestoreUsingCallback(object : FirebaseCallback {
//            override fun onResponse(response: Response) {
//                print(response)
//            }
//        })
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

        listView.setOnItemClickListener { parent, view, position, id ->
            val element = listView.adapter.getItemId(position) // The item that was clicked
            val intent = Intent(this, CollectedFileActivity::class.java)

            intent.putExtra(COLLECTEDDATA_SELECTED, response.collectedData?.get(element.toInt()))
//            intent.putExtra(FILES_JSON, response.collectedData?.get(element.toInt())?.movement?.files.toString())


            startActivity(intent)
        }

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
            return p0.toLong()
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        // render each line
        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            val rowList = LayoutInflater.from(mContext).inflate(R.layout.row_listview, p2, false)

            val row = mResponse.collectedData?.get(p0)

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
            gasSmokeTextView.text = row.gas_smoke.toString() + " ppm"

            // Movement
            val movementTextView = rowList.findViewById<TextView>(R.id.movement)
            movementTextView.text = Helper.booleanToNoYes(row.movement?.movDetected!!)

            // Image
//            val imageView = rowList.findViewById<ImageView>(R.id.imageMov1)
////            imageTextView.setImageURI(row.movement?.files?.get(0)?.urlFile!!.toUri())
//
//            row.movement?.files?.forEach {
//                Glide.with(mContext).load(it?.urlFile).into(imageView);
//            }

//            Glide.with(mContext).load(row.movement?.files?.get(1)?.urlFile).into(imageView);

//            Glide.with(mContext).load("https://firebasestorage.googleapis.com/v0/b/dentisvet-f3655.appspot.com/o/imagensAtendimentos%2F-MpmGC3fFT8q9dK_pD5u%2F1638299617178.jpg?alt=media&token=f39cae77-c753-4b03-b30c-b76e76b26a66").
//                into(imageView);



            return rowList
        }
    }
}