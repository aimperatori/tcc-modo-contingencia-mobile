package com.example.homeassistantoff.pager

import android.os.Bundle
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.homeassistantoff.R
import com.example.homeassistantoff.data.CollectedData
import com.example.homeassistantoff.data.Files
import com.example.homeassistantoff.pager.ViewPagerActivity.ScreenSlidePagerAdapter
import com.example.homeassistantoff.utils.Constants

class ViewPagerActivity : FragmentActivity() {

    private lateinit var horizontalPager: ViewPager
    private var collectedData: CollectedData? = null

    //    public ViewPagerActivity() {
    //        super(pager_title, view_pager, Arrays.asList(
    //                new Page(pager_p1_subtitle, pager_p1_text),
    //                new Page(pager_p2_subtitle, pager_p2_text)
    //        ));
    //    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_pager);

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

    //    @Override
    //    protected void onPageChanged(int page) {
    //        if (getPage() == 0) {
    //            findViewById(R.id.horizontal_pager).setVisibility(View.VISIBLE);
    //            findViewById(R.id.vertical_pager).setVisibility(View.GONE);
    //        } else {
    //            findViewById(R.id.horizontal_pager).setVisibility(View.GONE);
    //            findViewById(R.id.vertical_pager).setVisibility(View.VISIBLE);
    //        }
    //    }
    private inner class ScreenSlidePagerAdapter internal constructor(fm: FragmentManager?, files: List<Files>) :
        FragmentStatePagerAdapter(fm!!) {

        private val mFiles: List<Files> = files

        override fun getItem(position: Int): Fragment {
            val fragment = ViewPagerFragment()
            fragment.setAsset(mFiles[position].urlFile)
            return fragment
        }

        override fun getCount(): Int {
            return mFiles.size
        }
    }
}