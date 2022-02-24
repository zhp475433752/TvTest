package com.dyxc.tvtest.gridview

import android.os.Bundle
import androidx.fragment.app.FragmentActivity

class DbjMainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(android.R.id.content, DbjFragment())
                .commit()
        }
    }
}