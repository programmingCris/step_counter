package com.recrutation.fitsdktest.fit.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.recrutation.fitsdktest.R


class FitCircularView : View{
    private val START_ANGLE = -90f
    private val path = Path()
    private val progressPath = Path()
    private var trackPath : Path? = null
    private val progressPaint = Paint()
    private val trackPaint = Paint()
    private var progress = 0
    private var trackWidth = 10
    private var max = 1
    private var oval :RectF? = null

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

    fun setCustomMax(customMax : Int){
        this.max = customMax
    }

    fun getCustomMax(): Int{
        return max
    }

    fun setCustomProgress(customProgress: Int){
        this.progress = customProgress
        invalidate()
    }

    fun getCustomProgress(): Int{
        return progress
    }

    private fun init(context: Context, attrs: AttributeSet){
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CircleProgress,
            0, 0).apply {

            try {
                progressPaint.color = getColor(R.styleable.CircleProgress_customProgressColor, Color.YELLOW)
                trackPaint.color = getColor(R.styleable.CircleProgress_customTrackColor, Color.GRAY)
                progress = getInt(R.styleable.CircleProgress_customProgress, 0)
                trackWidth = getDimensionPixelSize(R.styleable.CircleProgress_customTrackWidth, 0)
                max = getInt(R.styleable.CircleProgress_customMax, 1)
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
        if(oval == null) {
            oval = RectF(mX - radius, mY - radius, mX + radius, mY + radius)
        }
        if(trackPath == null){
            trackPath = Path()
            trackPath?.addCircle(mX, mY, radius, Path.Direction.CCW)
        }
        canvas?.drawPath(trackPath!!, trackPaint)
        calculateProgress()
        canvas?.drawPath(progressPath, progressPaint)
    }

    private fun calculateProgress(){
        progressPath.reset()
        if(progress > max){
            progress %= max
        }
        val progressToShow = (360f * progress.toFloat() / max.toFloat())
        oval?.let { progressPath.addArc(it, START_ANGLE, progressToShow) }
    }
}