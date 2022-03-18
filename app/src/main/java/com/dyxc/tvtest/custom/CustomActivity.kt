package com.dyxc.tvtest.custom

import android.os.Bundle
import androidx.fragment.app.FragmentActivity

class CustomActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(android.R.id.content, CustomFragment())
                .commit()
        }
    }
}