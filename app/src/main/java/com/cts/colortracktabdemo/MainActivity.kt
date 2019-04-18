package com.cts.colortracktabdemo

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_main.*

/**
 * @Description: 主页
 * @suthor: CTS
 * @Date: 2019/04/12 10:08
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragments = arrayListOf<PageFragment>()
        fragments.add(PageFragment.newInstance(PageFragment.TYPE_UNFINISHED))
        fragments.add(PageFragment.newInstance(PageFragment.TYPE_FINISHED))
        val adapter = SimpleFragmentsPagerAdapter(fragments, supportFragmentManager)
        order_list_viewpager.adapter = adapter

        order_list_viewpager.offscreenPageLimit = 2
        order_list_viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                //根据viewpager滑动修改指示器动画,这里通过设置marginLeft实现滚动。（因为viewpager本身就是平滑滚动，所以这里也是伪平滑）
                val params = order_manager_shade.layoutParams as ViewGroup.MarginLayoutParams

                if (positionOffset != 0F) {
                    params.leftMargin = (positionOffset * (dip2px(this@MainActivity, 97F))).toInt()

                    //绘制左侧字体渐变
                    already_receiver_order.setDrawDirection(ColorTrackView.DrawDirection.RIGHT)
                        .setPercent(positionOffset)
                        .invalidate()

                    //绘制右侧字体渐变
                    already_finish_order.setDrawDirection(ColorTrackView.DrawDirection.LEFT)
                        .setPercent(positionOffset)
                        .invalidate()
                } else {
                    when (position) {
                        0 -> {
                            params.leftMargin = (dip2px(this@MainActivity, 0F))
                        }
                        else -> {
                            params.leftMargin = (dip2px(this@MainActivity, 97F))
                        }
                    }

                }

                // 请求重新对View进行measure、layout
                order_manager_shade.requestLayout()
            }

            override fun onPageSelected(position: Int) {
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })

        //指示器第一次需要把左侧的白色字绘制出来
        already_receiver_order.setDrawDirection(ColorTrackView.DrawDirection.RIGHT)
            .setPercent(0F)
            .invalidate()

        already_receiver_order.setOnClickListener {
            order_list_viewpager.setCurrentItem(0)
        }

        already_finish_order.setOnClickListener {
            order_list_viewpager.setCurrentItem(2)
        }

    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    fun dip2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    class SimpleFragmentsPagerAdapter(var fragments: List<PageFragment>, fm: FragmentManager) :
        FragmentPagerAdapter(fm) {


        override fun getItem(position: Int): PageFragment {
            return fragments[position]
        }

        override fun getCount(): Int {
            return fragments.size
        }

    }
}
