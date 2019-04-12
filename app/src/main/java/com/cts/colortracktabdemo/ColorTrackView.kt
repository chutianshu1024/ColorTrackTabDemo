package com.cts.colortracktabdemo

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.TextView

/**
 * @Description: 自定义 字体渐变的 导航栏指示器
 * @suthor: CTS
 * @Date: 2019/04/11 15:08
 */
class ColorTrackView : TextView {
    enum class DrawDirection {
        LEFT, RIGHT
    }

    var mDrawDirection: DrawDirection = DrawDirection.LEFT //需要绘制的部分
    var mPercent: Float = 0.0F//绘制百分比
    var mPaint: Paint? = null

    @JvmOverloads
    constructor(context: Context, attributeSet: AttributeSet? = null, defStyleAttr: Int = 0) : super(context, attributeSet, defStyleAttr)

    init {
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaint!!.color = resources.getColor(R.color.text_white)
        mPaint!!.textSize = textSize
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        //这里测量宽高参考 https://blog.csdn.net/u014702653/article/details/51985821
        val x = (width - mPaint!!.measureText(text.toString())) / 2
        val fontMetrics = mPaint!!.fontMetrics
        val y = height / 2 - fontMetrics.descent + (fontMetrics.bottom - fontMetrics.top) / 2

        //绘制。分为两种情况 1.绘制左侧  2.绘制右侧   根据DrawDirection字段判断
        canvas!!.save()
        if (mDrawDirection == DrawDirection.LEFT) {
            canvas!!.clipRect(0f, 0f, width * mPercent, height.toFloat())
        } else {
            canvas!!.clipRect(width * mPercent, 0f, width.toFloat(), height.toFloat())
        }
        canvas.drawText(text.toString(), x, y, mPaint)

        canvas!!.restore()
    }

    //设置绘制百分比
    fun setPercent(percent: Float): ColorTrackView {
        this.mPercent = percent
        return this
    }

    //设置需绘制部分（左侧还是右侧）
    fun setDrawDirection(drawDirection: DrawDirection): ColorTrackView {
        this.mDrawDirection = drawDirection
        return this
    }
}