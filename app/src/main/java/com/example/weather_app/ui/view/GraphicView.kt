package com.example.weather_app.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

class GraphicView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var stepVertical: Float = 15F
    private var stepHorizontal: Float = 100F

    private var initialPadding: Float = 20F

    private val data = mutableListOf<Pair<String, Int>>()

    private val vertPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = Color.BLACK
        strokeWidth = 4F
    }
    private val graphicPath = Path()

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.run {
            graphicPath.reset()

            val max = data.maxBy { it.second }?.second ?: 0
            val min = data.minBy { it.second }?.second ?: 0
            val rows = max - min
            stepVertical = (height.toFloat() - initialPadding * 2) / rows
            stepHorizontal = (width.toFloat() - initialPadding * 2) / data.size

            graphicPath.moveTo(initialPadding, initialPadding + max * stepVertical - data.first().second * stepVertical)
            data.forEachIndexed { index, pair ->
                try {
                    graphicPath.rLineTo(stepHorizontal, 0F)
                    graphicPath.rLineTo(0F, (pair.second - data[index + 1].second) * stepVertical)
                } catch (e: Exception) {
                }
            }
            drawPath(graphicPath, vertPaint)
        }
    }

    fun setData(data: List<Pair<String, Int>>) {
        this.data.apply {
            clear()
            addAll(data)
        }
        invalidate()
    }

}