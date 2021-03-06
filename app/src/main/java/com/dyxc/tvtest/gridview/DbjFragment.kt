package com.dyxc.tvtest.gridview

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.leanback.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.dyxc.tvtest.R

/**
 * Created by zhanghuipeng on 2022/2/15.
 */
class DbjFragment: Fragment() {
    private val TAG = "DbjFragment"
    lateinit var dbjGridView: VerticalGridView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = layoutInflater.inflate(R.layout.fragment_dbj_main, container, false)
        initView(root)
        initData()
        return root
    }

    private fun initData() {
        val list = mutableListOf<LessonBean>()
        val presenter = TPresenter()
        //创建ObjectAdapter，用于提供数据，当有多种类型时，传入PresenterSelector
        val objectAdapter = ArrayObjectAdapter(presenter)
        val url1 = "https://tenfei03.cfp.cn/creative/vcg/800/new/VCG41N1091576174.jpg"
        val url2 = "https://tenfei05.cfp.cn/creative/vcg/veer/800/new/VCG41N177546665.jpg"
        val url3 = "https://tenfei02.cfp.cn/creative/vcg/veer/1600water/veer-320732696.jpg"

        val v1 = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"
        val v2 = "https://media.w3.org/2010/05/sintel/trailer.mp4"
        val v3 = "http://vfx.mtime.cn/Video/2021/07/09/mp4/210709172715355157.mp4"

        for (i in 0..50) {
            var url = url1
            var vUrl = v1
            when (i % 3) {
                0 -> {
                    url = url1
                    vUrl = v1
                }
                1 -> {
                    url = url2
                    vUrl = v2
                }
                2 -> {
                    url = url3
                    vUrl = v3
                }
            }
            val bean = LessonBean(url, "这是标题 - $i", vUrl)
            objectAdapter.add(bean)
        }
        //通过前面创建的objectAdapter创建ItemBridgeAdapter，完成数据的传递
        val itemBridgeAdapter = ItemBridgeAdapter(objectAdapter)
        dbjGridView.adapter = itemBridgeAdapter
        dbjGridView.requestFocus()
        //设置动画
//        FocusHighlightHelper.setupHeaderItemFocusHighlight(itemBridgeAdapter)
        // item设置为可聚焦 android:focusableInTouchMode="true"
        FocusHighlightHelper.setupBrowseItemFocusHighlight(itemBridgeAdapter, FocusHighlight.ZOOM_FACTOR_LARGE, true)
    }

    private fun initView(root: View) {
        dbjGridView = root.findViewById(R.id.dbjGridView)
        dbjGridView.setNumColumns(4)//列数
        dbjGridView.setItemSpacing(50)//间距
        dbjGridView.setGravity(Gravity.CENTER)//对齐方式
        dbjGridView.setOnChildViewHolderSelectedListener(object :
            OnChildViewHolderSelectedListener() {
            override fun onChildViewHolderSelected(
                parent: RecyclerView?,
                child: RecyclerView.ViewHolder?,
                position: Int,
                subposition: Int
            ) {
                super.onChildViewHolderSelected(parent, child, position, subposition)
                Log.d(TAG, "-------onChildViewHolderSelected--------$position")
            }

            override fun onChildViewHolderSelectedAndPositioned(
                parent: RecyclerView?,
                child: RecyclerView.ViewHolder?,
                position: Int,
                subposition: Int
            ) {
                super.onChildViewHolderSelectedAndPositioned(parent, child, position, subposition)
                Log.d(TAG, "-------onChildViewHolderSelectedAndPositioned--------$position")
            }
        })


    }
}

