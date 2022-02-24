package com.dyxc.tvtest.video.exo

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.FragmentActivity
import com.dyxc.tvtest.R

class ExoPlayerActivity : FragmentActivity() {
    val SENSOR_SERVICE = "sensor"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exo_player)
        val url = intent.getStringExtra("url")
        val id = intent.getStringExtra("id")
        if (TextUtils.isEmpty(url) || TextUtils.isEmpty(id)) finish()
        if (savedInstanceState == null) {
            val beginTransaction = supportFragmentManager.beginTransaction()
            beginTransaction.add(R.id.videoFragment, ExoPlayerFragment.newInstance(url!!, id!!), ExoPlayerFragment.TAG)
            beginTransaction.commit()
        }
    }
}