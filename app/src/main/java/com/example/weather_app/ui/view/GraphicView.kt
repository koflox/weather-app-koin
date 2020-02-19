package com.example.weather_app.ui.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.weather_app.R

class GraphicView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var stepVertical: Float = 15F
    private var stepHorizontal: Float = 100F
    private var textSize: Float = context.resources
        .getDimensionPixelSize(R.dimen.text_size_small_plus)
        .toFloat()

    private var initialPadding: Float = 20F

    private val data = mutableListOf<Pair<String, Int>>()

    private val paintGraphic = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = Color.BLACK
        strokeWidth = 4F
    }
    private val paintText = Paint().apply {
        style = Paint.Style.FILL
        color = Color.BLACK
        textSize = this@GraphicView.textSize
    }
    private val graphicPath = Path()

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.run {
            drawGraphic(canvas)
        }
    }

    fun setData(data: List<Pair<String, Int>>) {
        this.data.apply {
            clear()
            addAll(data)
        }
        invalidate()
    }

    private fun drawGraphic(canvas: Canvas) {
        graphicPath.reset()

        //init step of vertical/horizontal segments for graphic
        val max = data.maxBy { it.second }?.second ?: 0
        val min = data.minBy { it.second }?.second ?: 0
        val rows = max - min
        stepVertical = (height.toFloat() - initialPadding * 2 - textSize * 2) / rows
        stepHorizontal = width.toFloat() / data.size

        //init path reference point
        var currentX = 0F
        val firstItemHeight = max * stepVertical - data.first().second * stepVertical
        var currentY = initialPadding + textSize + firstItemHeight

        //draw temp fo first item
        val textBottomPadding = textSize / 4
        val rect = Rect()
        var text = data.first().second.toString()
        paintText.getTextBounds(text, 0, text.length, rect)
        var x = stepHorizontal / 2F - rect.width() / 2F - rect.left
        var y = textSize / 2F - rect.height() / 2F - rect.bottom
        canvas.drawText(text, currentX + x, currentY - y - textBottomPadding, paintText)

        //draw time fo first item
        text = data.first().first
        paintText.getTextBounds(text, 0, text.length, rect)
        x = stepHorizontal / 2F - rect.width() / 2F - rect.left
        y = textSize / 2F - rect.height() / 2F - rect.bottom
        canvas.drawText(text, currentX + x, height - y - textBottomPadding, paintText)

        graphicPath.moveTo(currentX, currentY)
        data.forEachIndexed { index, pair ->
            try {
                //draw horizontal line
                currentX += stepHorizontal
                graphicPath.lineTo(currentX, currentY)

                //draw vertical line
                val segmentHeightDiff = (pair.second - data[index + 1].second) * stepVertical
                currentY += segmentHeightDiff
                graphicPath.lineTo(currentX, currentY)

                //draw temp
                val rect = Rect()
                var text = data[index + 1].second.toString()
                paintText.getTextBounds(text, 0, text.length, rect)
                var x = stepHorizontal / 2F - rect.width() / 2F - rect.left
                var y = textSize / 2F - rect.height() / 2F
                canvas.drawText(text, currentX + x, currentY - y - textBottomPadding, paintText)

                //draw time
                text = data[index + 1].first
                paintText.getTextBounds(text, 0, text.length, rect)
                x = stepHorizontal / 2F - rect.width() / 2F - rect.left
                y = textSize / 2F - rect.height() / 2F
                canvas.drawText(text, currentX + x, height - y - textBottomPadding, paintText)
            } catch (e: Exception) {
            }
        }
        canvas.drawPath(graphicPath, paintGraphic)
    }

}