package com.example.weather_app.ui.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.res.use
import com.example.weather_app.R

@Suppress("MemberVisibilityCanBePrivate")
@SuppressLint("Recycle")
class HeaderTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    companion object {
        val TAG = HeaderTextView::class.java.simpleName
    }

    var strokeColor: Int = Color.WHITE
        set(value) {
            field = value
            strikePaint.color = value
        }
    var strokeWidth: Float = 2F
        set(value) {
            field = value
            strikePaint.strokeWidth = value
        }
    var strokePaddingStart: Float = 0F
    var strokePaddingEnd: Float = 0F

    private val strikePaint: Paint = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = this@HeaderTextView.strokeWidth
        color = strokeColor
    }

    init {
        attrs?.run {
            context.obtainStyledAttributes(this, R.styleable.HeaderTextView, 0, 0).use {
                strokeColor = it.getColor(R.styleable.HeaderTextView_strokeColor, Color.WHITE)
                strokePaddingStart = it.getDimensionPixelSize(R.styleable.HeaderTextView_strokePaddingStart, 0).toFloat()
                strokePaddingEnd = it.getDimensionPixelSize(R.styleable.HeaderTextView_strokePaddingEnd, 0).toFloat()
                strokeWidth = it.getDimensionPixelSize(R.styleable.HeaderTextView_strokeWidth, 0).toFloat()
            }
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.run {
            drawStroke(this)
        }
    }

    private fun drawStroke(canvas: Canvas) {
        val yCenter = height / 2F
        val textWidth = paint.measureText(text.toString())
        val startX: Float
        val startY: Float
        val stopX: Float
        val stopY: Float
        when (gravity) {
            Gravity.TOP or Gravity.START -> {
                startX = textWidth + strokePaddingStart + paddingStart
                startY = yCenter
                stopX = width.toFloat() - strokePaddingEnd - paddingEnd
                stopY = yCenter
            }
            Gravity.TOP or Gravity.END -> {
                startX = strokePaddingStart + paddingStart
                startY = yCenter
                stopX = width.toFloat() - textWidth - strokePaddingEnd - paddingEnd
                stopY = yCenter
            }
            else -> return
        }
        if (stopX - startX < 0) {
            Log.e(TAG, "Skipping stroke! Stroke's end less than start, stopX: $stopX, startX: $startX.")
            return
        }
        canvas.drawLine(startX, startY, stopX, stopY, strikePaint)
    }

}