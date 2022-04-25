package com.dyxc.tvtest.custom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import com.dyxc.tvtest.R

/**
 * Created by zhanghuipeng on 2022/2/15.
 */
class CustomFragment: Fragment() {
    private val TAG = "DbjFragment"
    lateinit var image1: BesTvFrameLayout
    lateinit var image2: BesTvFrameLayout
    lateinit var image3: BesTvFrameLayout


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = layoutInflater.inflate(R.layout.fragment_custom, container, false)
        initView(root)
        initData()
        return root
    }

    private fun initData() {
    }

    private fun initView(root: View) {
        image1 = root.findViewById(R.id.image1)
        image2 = root.findViewById(R.id.image2)
        image3 = root.findViewById(R.id.image3)
        image1.requestFocus()
    }
}

