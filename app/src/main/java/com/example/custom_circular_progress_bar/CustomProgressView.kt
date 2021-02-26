package com.example.custom_circular_progress_bar

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import androidx.core.content.ContextCompat

@SuppressLint("AppCompatCustomView")
class RunProgress (context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private val strokeWidth = 2f

    private val emptyWellFill = ContextCompat.getColor(context, R.color.run_progress_empty)
    private val emptyWellStroke = ContextCompat.getColor(context, R.color.run_progress_empty)

    private val progressStroke = ContextCompat.getColor(context, R.color.run_progress_empty)
    private val progressFill = ContextCompat.getColor(context, R.color.run_progress_fill)

    private var fillPaint = Paint()
    private var strokePaint = Paint()

    private var currentStroke = emptyWellStroke
    private var currentFill = emptyWellFill

    init {
        fillPaint.isAntiAlias = true
        strokePaint.isAntiAlias = true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val w = width
        val h = height
        val pl = paddingLeft
        val pr = paddingRight
        val pt = paddingTop
        val pb = paddingBottom
        val usableWidth = w - (pl + pr)
        val usableHeight = h - (pt + pb)
        val radius = Math.min(usableWidth, usableHeight) / 2
        //val radius = 50
        val topRightRadius = 2
        val cx = pl + usableWidth / 2
        val cy = pt + usableHeight / 2
        fillPaint.style = Paint.Style.FILL
        fillPaint.color = currentFill

        strokePaint.strokeWidth = strokeWidth
        strokePaint.style = Paint.Style.STROKE
        strokePaint.color = currentStroke

        //val shapeBounds = RectFFactory.fromLTWH(0f, 0f, width, height)

        //canvas.drawRect()

        canvas.drawCircle(cx.toFloat(), cy.toFloat(), radius.toFloat(), fillPaint)
        canvas.drawCircle(cx.toFloat(), cy.toFloat(), radius.toFloat(), strokePaint)
    }

    fun setUpAsFill(delay: Boolean = true) {
        currentFill = progressFill
        currentStroke = progressStroke
        startAnimation(getScaleAnimation(delay))
    }

    fun resetProgress() {
        invalidate()
        currentFill = emptyWellFill
        currentStroke = emptyWellStroke
        //clearAnimation()
    }

    private fun getScaleAnimation(delay: Boolean): ScaleAnimation {
        val delayDuration = if(delay) 1000L else 250L
        return ScaleAnimation(
            1f, 1.8f, 1f, 1.8f,
            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
        ).apply {
            duration = delayDuration
            fillAfter = true
        }
    }
}