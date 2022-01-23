package com.example.homeassistantoff.collectedFile

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.homeassistantoff.R

import com.example.homeassistantoff.utils.Constants.TAG
import com.example.homeassistantoff.utils.Helper
import androidx.lifecycle.ViewModelProvider
import com.example.homeassistantoff.data.*
import com.example.homeassistantoff.utils.Constants.COLLECTEDDATA_SELECTED


class CollectedFileActivity : AppCompatActivity() {

    private lateinit var viewModel: CollectedFileViewModel
    private lateinit var listView: ListView
    private var collectedData : CollectedData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collectedfiles)

        collectedData = intent?.getParcelableExtra(COLLECTEDDATA_SELECTED)!!

        Toast.makeText(baseContext, collectedData?.createdDateTime + collectedData?.movement?.movDetected, Toast.LENGTH_LONG).show()



        listView = findViewById<ListView>(R.id.collectedFile_listView)

        this.print(collectedData!!)

//        viewModel = ViewModelProvider(this, CollectedFileViewModelFactory(index.toString())).get(
//                CollectedFileViewModel::class.java
//            )


//        viewModel = ViewModelProvider(this).get(CollectedFileViewModel::class.java)
//        viewModel = CollectedFileViewModel(index!!)

//        getResponseUsingCallback()
//            getResponseUsingLiveData()
//            getResponseUsingCoroutines()


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

    private fun print(collectedData: CollectedData) {

        listView.adapter = MyCustomAdapter(this, collectedData.movement?.files!!)

    }

    private class MyCustomAdapter(context: Context, files: List<Files>) : BaseAdapter() {

        private val mContext: Context = context
        private val mFiles: List<Files> = files

        // numero de linhas
        override fun getCount(): Int {
            return mFiles.size!!
        }

        override fun getItem(p0: Int): Any {
            return p0.toLong()
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        // render each line
        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            val rowList = LayoutInflater.from(mContext).inflate(R.layout.rowfiles_listview, p2, false)

            val row = mFiles[p0]

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

