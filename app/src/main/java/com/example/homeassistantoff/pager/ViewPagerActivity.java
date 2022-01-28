//package com.example.homeassistantoff.pager;
//
//import android.os.Bundle;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentActivity;
//import androidx.fragment.app.FragmentManager;
//import androidx.fragment.app.FragmentStatePagerAdapter;
//import androidx.viewpager.widget.ViewPager;
//import com.example.homeassistantoff.R;
//
//
//public class ViewPagerActivity extends FragmentActivity {
//
//    private static final String[] IMAGES = { "sanmartino.jpg", "swissroad.jpg" };
//
////    public ViewPagerActivity() {
////        super(pager_title, view_pager, Arrays.asList(
////                new Page(pager_p1_subtitle, pager_p1_text),
////                new Page(pager_p2_subtitle, pager_p2_text)
////        ));
////    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        ViewPager horizontalPager = findViewById(R.id.horizontal_pager);
//        horizontalPager.setAdapter(new ScreenSlidePagerAdapter(getSupportFragmentManager()));
//    }
//
//    @Override
//    public void onBackPressed() {
//        ViewPager viewPager = findViewById(R.id.horizontal_pager);
//        if (viewPager.getCurrentItem() == 0) {
//            super.onBackPressed();
//        } else {
//            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
//        }
//    }
//
////    @Override
////    protected void onPageChanged(int page) {
////        if (getPage() == 0) {
////            findViewById(R.id.horizontal_pager).setVisibility(View.VISIBLE);
////            findViewById(R.id.vertical_pager).setVisibility(View.GONE);
////        } else {
////            findViewById(R.id.horizontal_pager).setVisibility(View.GONE);
////            findViewById(R.id.vertical_pager).setVisibility(View.VISIBLE);
////        }
////    }
//
//    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
//        ScreenSlidePagerAdapter(FragmentManager fm) {
//            super(fm);
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            ViewPagerFragment fragment = new ViewPagerFragment();
//            fragment.setAsset(IMAGES[position]);
//            return fragment;
//        }
//
//        @Override
//        public int getCount() {
//            return IMAGES.length;
//        }
//    }
//}