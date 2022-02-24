package com.dyxc.tvtest.video.ui

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.widget.Toast
import androidx.fragment.app.FragmentActivity

/** Loads [PlaybackVideoFragment]. */
class PlaybackActivity : FragmentActivity() {
    private val TAG = "PlaybackActivity"
    var mPlaybackFragment:PlaybackVideoFragment? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            val playbackVideoFragment = PlaybackVideoFragment()
            mPlaybackFragment = playbackVideoFragment
            supportFragmentManager.beginTransaction()
                .replace(android.R.id.content, playbackVideoFragment)
                .commit()
        }
    }

    /**
     * 因为只有一个播放和暂停按钮，所以 快进/快退/上一个/下一个没有
     * 如果需要实现这些功能，请参考：https://github.com/android/tv-samples的Leanback项目，自定义PlaybackTransportControlGlue
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        Log.d(TAG, "遥控器按键 --- $keyCode")
//        Toast.makeText(this, "按键 - $keyCode", Toast.LENGTH_SHORT).show()
        if (keyCode == KeyEvent.KEYCODE_BUTTON_R1) {
            mPlaybackFragment?.skipToNext()
            return true
        } else if (keyCode == KeyEvent.KEYCODE_BUTTON_L1) {
            mPlaybackFragment?.skipToPrevious()
            return true
        } else if (keyCode == KeyEvent.KEYCODE_BUTTON_L2) {
            mPlaybackFragment?.rewind()
        } else if (keyCode == KeyEvent.KEYCODE_BUTTON_R2) {
            mPlaybackFragment?.fastForward()
        }
        return super.onKeyDown(keyCode, event)
    }
}