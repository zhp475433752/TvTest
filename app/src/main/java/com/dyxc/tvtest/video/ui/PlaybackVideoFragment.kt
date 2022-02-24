package com.dyxc.tvtest.video.ui

import android.net.Uri
import android.os.Bundle
import androidx.leanback.app.VideoSupportFragment
import androidx.leanback.app.VideoSupportFragmentGlueHost
import androidx.leanback.media.MediaPlayerAdapter
import androidx.leanback.media.PlaybackTransportControlGlue
import androidx.leanback.widget.PlaybackControlsRow
import com.dyxc.tvtest.DetailsActivity
import com.dyxc.tvtest.Movie
import java.util.concurrent.TimeUnit

/** Handles video playback with media controls. */
class PlaybackVideoFragment : VideoSupportFragment() {

    private lateinit var mTransportControlGlue: PlaybackTransportControlGlue<MediaPlayerAdapter>
    private lateinit var playerAdapter: MediaPlayerAdapter
    private val TEN_SECONDS = TimeUnit.SECONDS.toMillis(10)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val (_, title, description, _, _, videoUrl) =
            activity?.intent?.getSerializableExtra(DetailsActivity.MOVIE) as Movie

        val glueHost = VideoSupportFragmentGlueHost(this@PlaybackVideoFragment)
        playerAdapter = MediaPlayerAdapter(activity)
        playerAdapter.setRepeatAction(PlaybackControlsRow.RepeatAction.INDEX_ALL)

        mTransportControlGlue = PlaybackTransportControlGlue(getActivity(), playerAdapter)
        mTransportControlGlue.host = glueHost
        mTransportControlGlue.title = title
        mTransportControlGlue.subtitle = description
        mTransportControlGlue.playWhenPrepared()

        playerAdapter.setDataSource(Uri.parse(videoUrl))
    }

    override fun onPause() {
        super.onPause()
        mTransportControlGlue.pause()
    }

    //下一个
    fun skipToNext() {
        mTransportControlGlue.next()
    }

    //上一个
    fun skipToPrevious() {
        mTransportControlGlue.previous()
    }

    //快退
    fun rewind() {
        var newPosition: Long = playerAdapter.currentPosition - TEN_SECONDS
        newPosition = if (newPosition < 0) 0 else newPosition
        playerAdapter.seekTo(newPosition)
    }

    //快进
    fun fastForward() {
        if (playerAdapter.duration > -1) {
            var newPosition: Long = playerAdapter.currentPosition + TEN_SECONDS
            newPosition =
                if (newPosition > playerAdapter.getDuration()) playerAdapter.getDuration() else newPosition
            playerAdapter.seekTo(newPosition)
        }
    }


}