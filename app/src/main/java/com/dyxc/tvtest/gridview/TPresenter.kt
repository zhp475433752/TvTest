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
import java.security.MessageDigest

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
                    intent.putExtra("id", transformMD5(item.videoUrl))
                    imageView.context.startActivity(intent)
                    Toast.makeText(imageView.context!!, "点击了 --- ${item.title}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onUnbindViewHolder(viewHolder: ViewHolder?) {

    }

    /**
     * @param inputStr
     * @return 32位的MD5数
     */
    private fun transformMD5(inputStr: String): String? {
        var md5: MessageDigest? = null
        md5 = try {
            MessageDigest.getInstance("MD5")
        } catch (e: Exception) {
            println(e.toString())
            return null
        }
        val charArray = inputStr.toCharArray() //将字符串转换为字符数组
        val byteArray = ByteArray(charArray.size) //创建字节数组
        for (i in charArray.indices) {
            byteArray[i] = charArray[i].toByte()
        }

        //将得到的字节数组进行MD5运算
        val md5Bytes: ByteArray = md5!!.digest(byteArray)
        val md5Str = StringBuffer()
        for (i in md5Bytes.indices) {
            if (Integer.toHexString(0xFF and md5Bytes[i].toInt()).length == 1) md5Str.append("0")
                .append(
                    Integer.toHexString(
                        0xFF and md5Bytes[i]
                            .toInt()
                    )
                ) else md5Str.append(Integer.toHexString(0xFF and md5Bytes[i].toInt()))
        }
        return md5Str.toString()
    }
}