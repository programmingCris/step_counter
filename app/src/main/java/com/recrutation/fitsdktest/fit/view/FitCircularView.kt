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




class FitCircularView : View{
    val path = Path()
    val progressPath = Path()
    val trackPath = Path()
    private val progressPaint = Paint()
    private val trackPaint = Paint()

    init {
        progressPaint.color = Color.BLACK
        trackPaint.color = Color.GREEN
        progressPaint.style = Paint.Style.STROKE
        trackPaint.style = Paint.Style.STROKE
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        circleDraw(canvas)
    }

    private fun circleDraw(canvas: Canvas?){
        val radius = (measuredWidth/2).toFloat()
        val mX = (measuredWidth/2).toFloat()
        val mY = (measuredHeight/2).toFloat()
        val oval = RectF(mX - radius, mY - radius, mX + radius, mY + radius)
        canvas?.drawArc(oval, -90F, 90F, false, progressPaint)
        canvas?.drawArc(oval, -190F, 89F, false, trackPaint)
    }
}