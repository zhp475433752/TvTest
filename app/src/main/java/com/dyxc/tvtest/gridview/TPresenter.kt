package com.dyxc.tvtest.gridview

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.leanback.widget.Presenter
import com.bumptech.glide.Glide
import com.dyxc.tvtest.R
import com.dyxc.tvtest.video.exo.ExoPlayerActivity

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
                Glide.with(imageView?.context!!).load(item.imageUrL).into(imageView)
                textView?.text = item.title
                viewHolder.view?.setOnClickListener {
                    val intent = Intent(imageView.context, ExoPlayerActivity::class.java)
                    intent.putExtra("url", item.videoUrl)
                    intent.putExtra("id", "01")
                    imageView.context.startActivity(intent)
                    Toast.makeText(imageView.context!!, "点击了 --- ${item.title}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onUnbindViewHolder(viewHolder: ViewHolder?) {

    }
}