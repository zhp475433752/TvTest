package com.dyxc.tvtest.custom

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.core.view.ViewCompat
import com.dyxc.tvtest.R
import java.util.*

/**
 * Created by zhanghuipeng on 2022/3/18.
 */
class BesTvFrameLayout : FrameLayout, View.OnFocusChangeListener {
    /**
     * 缩放大小，参考
    <item name="lb_focus_zoom_factor_large" type="fraction">118%</item>
    <item name="lb_focus_zoom_factor_medium" type="fraction">114%</item>
    <item name="lb_focus_zoom_factor_small" type="fraction">110%</item>
    <item name="lb_focus_zoom_factor_xsmall" type="fraction">106%</item>
     */
    private val mScale = 1.18f
    constructor(context: Context):super(context)
    constructor(context: Context, attrs: AttributeSet?):super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int):super(context, attrs, defStyleAttr)
    init {
        isFocusableInTouchMode = true
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            focusable  = FOCUSABLE
        }
        onFocusChangeListener = this
        setBackgroundResource(R.drawable.bg_custom_selector)
    }

    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        if (hasFocus) {
            ViewCompat.animate(v!!).scaleX(mScale).scaleY(mScale).translationZ(1f).start()
        } else {
            ViewCompat.animate(v!!).scaleX(1f).scaleY(1f).translationZ(1f).start()
        }
    }
}