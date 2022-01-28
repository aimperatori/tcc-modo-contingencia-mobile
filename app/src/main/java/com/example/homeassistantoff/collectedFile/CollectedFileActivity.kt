package com.example.homeassistantoff.collectedFile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.davemorrissey.labs.subscaleview.ImageSource
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView
import com.example.homeassistantoff.R
import com.example.homeassistantoff.data.*
import com.example.homeassistantoff.utils.Constants.COLLECTEDDATA_SELECTED
import com.example.homeassistantoff.utils.Helper
import android.R.id
import android.graphics.Bitmap

import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition


class CollectedFileActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private var collectedData: CollectedData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_collectedfiles)
        setContentView(R.layout.activity_files)

        collectedData = intent?.getParcelableExtra(COLLECTEDDATA_SELECTED)!!

//        listView = findViewById(R.id.collectedFile_listView)

        this.renderUI(collectedData!!)

//        view_pager.horizontal_pager
    }

    private fun renderUI(collectedData: CollectedData) {

//        val imageView = findViewById<SubsamplingScaleImageView>(R.id.imageView_lib);
        //imageView.adapter = MyCustomAdapter(this, collectedData.movement?.files!!.filterNotNull())

        val imageView = findViewById<SubsamplingScaleImageView>(R.id.imageView_lib);

        collectedData.movement?.files?.filterNotNull()?.forEach() {
            Glide.with(this)
                .asBitmap()
                .load(it.urlFile)
                .into(object : SimpleTarget<Bitmap?>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap?>?
                    ) {
                        imageView.setImage(ImageSource.cachedBitmap(resource))
                    }
                })
        }


//        val urlFile = "https://firebasestorage.googleapis.com/v0/b/homeassistantoff.appspot.com/o/Dht22.jpg?alt=media&token=1de3a389-15c1-46bd-84ae-d1ffdd24d11e"
//        val imageView = findViewById<SubsamplingScaleImageView>(R.id.imageView_lib);
//
//        Glide.with(this)
//            .asBitmap()
//            .load(urlFile)
//            .into(object : SimpleTarget<Bitmap?>() {
//                override fun onResourceReady(
//                    resource: Bitmap,
//                    transition: Transition<in Bitmap?>?
//                ) {
//                    imageView.setImage(ImageSource.cachedBitmap(resource))
//                }
//            })

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
            val rowList = LayoutInflater.from(mContext).inflate(R.layout.activity_files, p2, false)

            val row = mFiles[p0]

            val imageView = rowList.findViewById<SubsamplingScaleImageView>(R.id.imageView_lib);

            Glide.with(mContext)
                .asBitmap()
                .load(row?.urlFile)
                .into(object : SimpleTarget<Bitmap?>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap?>?
                    ) {
                        // you can do something with loaded bitmap here
                        imageView.setImage(ImageSource.cachedBitmap(resource))
//                    contentImage.setImageBitmap(resource)
//                    bitmap = resource
                    }
                })

//            if (Helper.isImage(row?.extension!!)) {
//                val imageView = rowList.findViewById<ImageView>(R.id.imageView)
//                Glide.with(mContext).load(row?.urlFile).into(imageView)
//
//            }
//            else if (Helper.isVideo(row?.extension!!)) {
//                val videoView = rowList.findViewById<VideoView>(R.id.videoView)
//
//                var mediaController = MediaController(mContext)
//                mediaController.setAnchorView(videoView)
//
//                videoView.setMediaController(mediaController)
//                videoView.setVideoURI(row?.urlFile?.toUri())
//            }

            return rowList
        }
    }
}