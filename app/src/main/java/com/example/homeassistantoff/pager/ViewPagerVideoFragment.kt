package com.example.homeassistantoff.pager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.VideoView
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import com.example.homeassistantoff.R

class ViewPagerVideoFragment : Fragment() {
    private var asset: String? = null

    fun setAsset(asset: String?) {
        this.asset = asset
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.view_pager_video, container, false)
        if (savedInstanceState != null) {
            if (asset == null && savedInstanceState.containsKey(BUNDLE_ASSET)) {
                asset = savedInstanceState.getString(BUNDLE_ASSET)
            }
        }
        if (asset != null) {
            val videoView: VideoView = rootView.findViewById(R.id.videoView)

            val mediaController = MediaController(activity)
            mediaController.setAnchorView(videoView)

            videoView.setMediaController(mediaController)
            videoView.setVideoURI(asset?.toUri())
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