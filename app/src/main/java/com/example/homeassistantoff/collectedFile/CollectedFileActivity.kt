package com.example.homeassistantoff.collectedFile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.homeassistantoff.R
import com.example.homeassistantoff.data.*
import com.example.homeassistantoff.utils.Constants.COLLECTEDDATA_SELECTED


class CollectedFileActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private var collectedData : CollectedData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collectedfiles)

        collectedData = intent?.getParcelableExtra(COLLECTEDDATA_SELECTED)!!

        listView = findViewById(R.id.collectedFile_listView)

        this.renderUI(collectedData!!)
    }

    private fun renderUI(collectedData: CollectedData) {
        listView.adapter = MyCustomAdapter(this, collectedData.movement?.files!!)
    }

    private class MyCustomAdapter(context: Context, files: List<Files>) : BaseAdapter() {

        private val mContext: Context = context
        private val mFiles: List<Files> = files

        // number of rows
        override fun getCount(): Int {
            return mFiles.size
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

            return rowList
        }
    }
}