package com.example.homeassistantoff.collectedFile

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.homeassistantoff.R
import com.example.homeassistantoff.data.FirebaseCallback
import com.example.homeassistantoff.data.FirebaseFilesCallback
import com.example.homeassistantoff.data.Response
import com.example.homeassistantoff.data.ResponseFiles
import com.example.homeassistantoff.utils.Constants.DATA_INDEX
import com.example.homeassistantoff.utils.Constants.TAG
import com.example.homeassistantoff.utils.Helper

class CollectedFileActivity : AppCompatActivity() {

    private lateinit var viewModel: CollectedFileViewModel
    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collectedfiles)
//        viewModel = ViewModelProvider(this).get(CollectedFileViewModel::class.java)

        val index = intent?.getStringExtra(DATA_INDEX)

        viewModel = CollectedFileViewModel(index!!)

        getResponseUsingCallback()
//            getResponseUsingLiveData()
//            getResponseUsingCoroutines()

        listView = findViewById<ListView>(R.id.collectedFile_listView)
    }

    private fun getResponseUsingCallback() {
        viewModel.getResponseUsingCallback(object : FirebaseFilesCallback {
            override fun onResponse(response: ResponseFiles) {
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

    private fun print(response: ResponseFiles) {

        listView.adapter = MyCustomAdapter(this, response)

        response.exception?.message?.let {
            Log.e(TAG, it)
        }
    }

    private class MyCustomAdapter(context: Context, response: ResponseFiles) : BaseAdapter() {

        private val mContext: Context = context
        private val mResponse: ResponseFiles = response

        // numero de linhas
        override fun getCount(): Int {
            return mResponse.files?.size!!
        }

        override fun getItem(p0: Int): Any {
            return p0.toLong()
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        // render each line
        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            val rowList = LayoutInflater.from(mContext).inflate(R.layout.activity_collectedfiles, p2, false)

            val row = mResponse.files?.get(p0)

            // Image
            val imageView = rowList.findViewById<ImageView>(R.id.imageView)

            Glide.with(mContext).load(row?.urlFile).into(imageView);


            // Image
            //            val imageTextView = rowList.findViewById<ImageView>(R.id.imageView)
            //
            ////            imageTextView.setImageBitmap(row.image)
            //
            //            val bitmap = BitmapFactory.dec(row?.image)
            //            imageTextView.setImageBitmap(bitmap)

            //                CoroutineScope(Dispatchers.IO).launch {
            //
            //                    try {
            //                        val imageTextView = rowList.findViewById<ImageView>(R.id.imageView)
            //                        val bitmap =
            //                            BitmapFactory.decodeStream(URL(row?.imageUrl).content as InputStream)
            //                        imageTextView.setImageBitmap(bitmap)
            //                    } catch (e: NetworkOnMainThreadException) {
            //                        e.printStackTrace()
            //                    } catch (e: IOException) {
            //                        e.printStackTrace()
            //                    }
            //                }
            //            }

            return rowList
        }
    }
}

