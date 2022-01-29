package com.example.homeassistantoff.pager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.homeassistantoff.R
import com.example.homeassistantoff.data.CollectedData
import com.example.homeassistantoff.data.Files
import com.example.homeassistantoff.utils.Constants
import com.example.homeassistantoff.utils.Helper


class ViewPagerActivity : AppCompatActivity() {

    private lateinit var horizontalPager: ViewPager
    private var collectedData: CollectedData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_pager)

        collectedData = intent?.getParcelableExtra(Constants.COLLECTEDDATA_SELECTED)!!

        var listFiles: List<Files> = collectedData?.movement?.files?.filterNotNull()!!

        horizontalPager = findViewById(R.id.horizontal_pager)
        horizontalPager.adapter = ScreenSlidePagerAdapter(supportFragmentManager, listFiles)
    }

    override fun onBackPressed() {
        val viewPager: ViewPager = findViewById(R.id.horizontal_pager)
        if (viewPager.currentItem == 0) {
            super.onBackPressed()
        } else {
            viewPager.currentItem = viewPager.currentItem - 1
        }
    }

    private inner class ScreenSlidePagerAdapter internal constructor(fm: FragmentManager?, files: List<Files>) :
        FragmentStatePagerAdapter(fm!!) {

        private val mFiles: List<Files> = files

        override fun getItem(position: Int): Fragment {

            lateinit var fragment : Fragment

            if (Helper.isImage(mFiles[position].extension!!))
            {
                fragment = ViewPagerImageFragment()
                fragment.setAsset(mFiles[position].urlFile)
            }
            else if (Helper.isVideo(mFiles[position].extension!!))
            {
                fragment = ViewPagerVideoFragment()
                fragment.setAsset(mFiles[position].urlFile)
            }

            return fragment
        }

        override fun getCount(): Int {
            return mFiles.size
        }
    }
}