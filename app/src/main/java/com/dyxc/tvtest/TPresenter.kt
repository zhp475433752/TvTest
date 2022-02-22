package com.dyxc.tvtest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.leanback.widget.Presenter
import com.bumptech.glide.Glide

/**
 * Created by zhanghuipeng on 2022/2/22.
 */
class TPresenter: Presenter() {
    override fun onCreateViewHolder(parent: ViewGroup?): ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_gridview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder?, item: Any?) {
        when (item) {
            is LessonBean -> {
                val imageView = viewHolder?.view?.findViewById<ImageView>(R.id.item_image)
                val textView = viewHolder?.view?.findViewById<TextView>(R.id.item_text)
                Glide.with(imageView?.context!!).load(item.url).into(imageView)
                textView?.text = item.title
                viewHolder.view?.setOnClickListener {
                    Toast.makeText(imageView.context!!, "点击了 --- ${item.title}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onUnbindViewHolder(viewHolder: ViewHolder?) {

    }
}