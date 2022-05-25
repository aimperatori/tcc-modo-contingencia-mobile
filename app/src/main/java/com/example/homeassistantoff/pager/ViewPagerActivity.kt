package com.example.homeassistantoff.pager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.homeassistantoff.R
import com.example.homeassistantoff.data.Camera
import com.example.homeassistantoff.utils.Constants


class ViewPagerActivity : AppCompatActivity() {

    private lateinit var horizontalPager: ViewPager
    private var camera: Camera? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_pager)

        camera = intent?.getParcelableExtra(Constants.CAMERA_SELECTED)!!

        var listFiles: List<String> = camera?.files?.filterNotNull()!!

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

    private inner class ScreenSlidePagerAdapter(fm: FragmentManager?, files: List<String>) :
        FragmentStatePagerAdapter(fm!!) {

        private val mFiles: List<String> = files

        override fun getItem(position: Int): Fragment {

            lateinit var fragment : Fragment

            fragment = ViewPagerImageFragment()
            fragment.setAsset(mFiles[position])

            return fragment
        }

        override fun getCount(): Int {
            return mFiles.size
        }
    }
}