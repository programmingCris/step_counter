package com.recrutation.fitsdktest.fit.view

import android.R.attr
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.green
import android.R.attr.radius

import android.graphics.RectF
import com.recrutation.fitsdktest.R


class FitCircularView : View{
    val path = Path()
    val progressPath = Path()
    val trackPath = Path()
    private val progressPaint = Paint()
    private val trackPaint = Paint()
    private var progress = 0
    private var trackWidth = 10

    init {
        progressPaint.style = Paint.Style.STROKE
        trackPaint.style = Paint.Style.STROKE
        progressPaint.isAntiAlias = true
        trackPaint.isAntiAlias = true
        progressPaint.strokeWidth = trackWidth.toFloat()
        trackPaint.strokeWidth = trackWidth.toFloat()
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs){
        init(context, attrs)
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr){
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet){
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CircleProgress,
            0, 0).apply {

            try {
                progressPaint.color = getColor(R.styleable.CircleProgress_custom_progressColor, Color.YELLOW)
                trackPaint.color = getColor(R.styleable.CircleProgress_custom_trackColor, Color.GRAY)
                progress = getInt(R.styleable.CircleProgress_custom_progress, 0)
                trackWidth = getDimensionPixelSize(R.styleable.CircleProgress_custom_trackWidth, 0)
            } finally {
                recycle()
            }
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        circleDraw(canvas)
    }

    private fun circleDraw(canvas: Canvas?){
        val margin = measuredWidth/ 10
        val radius = (measuredWidth/2).toFloat() - trackWidth - margin
        val mX = (measuredWidth/2).toFloat()
        val mY = (measuredHeight/2).toFloat()
        val oval = RectF(mX - radius, mY - radius, mX + radius, mY + radius)
        canvas?.drawArc(oval, 0F, 360F, false, trackPaint)
        canvas?.drawArc(oval, -90F, 90F, false, progressPaint)
    }
}