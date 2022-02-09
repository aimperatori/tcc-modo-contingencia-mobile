package com.example.homeassistantoff.pager

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.davemorrissey.labs.subscaleview.ImageSource
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView
import com.example.homeassistantoff.R

class ViewPagerImageFragment : Fragment() {
    private var asset: String? = null

    fun setAsset(asset: String?) {
        this.asset = asset
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.view_pager_page, container, false)
        if (savedInstanceState != null) {
            if (asset == null && savedInstanceState.containsKey(BUNDLE_ASSET)) {
                asset = savedInstanceState.getString(BUNDLE_ASSET)
            }
        }
        if (asset != null) {
            val imageView: SubsamplingScaleImageView = rootView.findViewById(R.id.imageView)

            Glide.with(imageView)
                .asBitmap()
                .load(asset)
                .into(object : SimpleTarget<Bitmap?>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap?>?
                    ) {
                        imageView.setImage(ImageSource.cachedBitmap(resource))
                    }
                })

        }
        return rootView
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val rootView = view
        if (rootView != null) {
            outState.putString(BUNDLE_ASSET, asset)
        }
    }

    companion object {
        private const val BUNDLE_ASSET = "asset"
    }
}