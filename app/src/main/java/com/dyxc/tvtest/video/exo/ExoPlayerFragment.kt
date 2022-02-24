package com.dyxc.tvtest.video.exo

import android.content.Context
import android.media.AudioManager
import android.media.AudioManager.OnAudioFocusChangeListener
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.leanback.app.*
import androidx.leanback.media.PlaybackGlue
import androidx.leanback.widget.PlaybackControlsRow
import com.dyxc.tvtest.R


private const val ARG_PARAM1 = "video_url"
private const val ARG_PARAM2 = "video_id"

/**
 * A simple [Fragment] subclass.
 * Use the [ExoPlayerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ExoPlayerFragment : VideoSupportFragment() {

//    private val URL = ("https://vod.douyuxingchen.com/6057e0cbvodtransbj1306665185/847f69ee3701925924906167486/v.f100830.mp4")
    private var mMediaPlayerGlue: VideoMediaPlayerGlue<ExoPlayerAdapter>? = null
    val mHost = VideoSupportFragmentGlueHost(this)

    companion object {
        val TAG = "ExoPlayerFragment"
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ExoPlayerFragment.
         */
        @JvmStatic
        fun newInstance(videoUrl: String, videoId: String) =
            ExoPlayerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, videoUrl)
                    putString(ARG_PARAM2, videoId)
                }
            }
    }

    private fun playWhenReady(glue: PlaybackGlue?) {
        if (glue == null) return
        if (glue.isPrepared) {
            glue.play()
        } else {
            glue.addPlayerCallback(object : PlaybackGlue.PlayerCallback() {
                override fun onPreparedStateChanged(glue: PlaybackGlue) {
                    if (glue.isPrepared) {
                        glue.removePlayerCallback(this)
                        glue.play()
                    }
                }
            })
        }
    }

    var mOnAudioFocusChangeListener = OnAudioFocusChangeListener { }

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        val playerAdapter = ExoPlayerAdapter(activity)
        playerAdapter.audioStreamType = AudioManager.USE_DEFAULT_STREAM_TYPE
        mMediaPlayerGlue = VideoMediaPlayerGlue(activity, playerAdapter)
        mMediaPlayerGlue?.setHost(mHost)
        val audioManager = activity?.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        if (audioManager.requestAudioFocus(
                mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN
            ) != AudioManager.AUDIOFOCUS_REQUEST_GRANTED
        ) {
            Log.w(TAG, "video player cannot obtain audio focus!")
        }

        mMediaPlayerGlue?.setMode(PlaybackControlsRow.RepeatAction.INDEX_NONE)
        mMediaPlayerGlue?.title = "窦老师-带你了解豆伴匠"
        mMediaPlayerGlue?.subtitle = "大唐奇闻录-01"
        mMediaPlayerGlue?.playerAdapter?.setDataSource(Uri.parse(param1))
        PlaybackSeekDiskDataProvider.setDemoSeekProvider(requireContext(), mMediaPlayerGlue, param1, param2)
        playWhenReady(mMediaPlayerGlue)
        backgroundType = PlaybackSupportFragment.BG_LIGHT
    }

    override fun onPause() {
        if (mMediaPlayerGlue != null) {
            mMediaPlayerGlue?.pause()
        }
        super.onPause()
    }
}