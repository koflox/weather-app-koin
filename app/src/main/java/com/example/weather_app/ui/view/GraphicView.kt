package com.example.weather_app.ui.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorRes
import com.example.weather_app.R
import com.example.weather_app.data.displayed.DisplayedWeatherItem
import java.util.*

class GraphicView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    enum class DataType {
        HOURLY_TEMP,
        PRECIPITATION,
        NONE
    }

    var textSize: Float = context.resources.getDimensionPixelSize(R.dimen.text_size_small).toFloat()
        set(value) {
            field = value
            textBottomPadding = value / 4
        }
    var dataType: DataType = DataType.NONE
    @ColorRes
    var colorGraphic: Int = android.R.color.black
        set(value) {
            field = value
            paintGraphic.color = value
        }
    @ColorRes
    var colorData: Int = android.R.color.black
        set(value) {
            field = value
            paintData.color = value
        }

    private var stepVertical: Float = 15F
    private var stepHorizontal: Float = 100F
    private var textBottomPadding = textSize / 4

    private var initialPadding: Float = 20F

    private val data = mutableListOf<DisplayedWeatherItem>()

    private val paintGraphic = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = Color.BLACK
        strokeWidth = 4F
    }
    private val paintData = Paint().apply {
        style = Paint.Style.FILL
        color = Color.BLACK
        textSize = this@GraphicView.textSize
    }
    private val graphicPath = Path()
    private var graphicPathCurrentX: Float = 0F
    private var graphicPathCurrentY: Float = 0F
    private val tempTextBoundingRect: Rect = Rect()

    init {
        attrs?.let {
            val attributes = context.obtainStyledAttributes(it, R.styleable.GraphicView, 0, 0)
            try {
                colorData = attributes.getColor(R.styleable.GraphicView_colorData, Color.BLACK)
                colorGraphic = attributes.getColor(R.styleable.GraphicView_colorGraphic, Color.BLACK)
            } finally {
                attributes.recycle()
            }
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.run {
            drawGraphic(this)
        }
    }

    fun setData(data: List<DisplayedWeatherItem>) {
        this.data.apply {
            clear()
            addAll(data)
        }
        invalidate()
    }

    @Suppress("IntroduceWhenSubject")
    private fun drawGraphic(canvas: Canvas) {
        graphicPath.reset()

        //init step of vertical/horizontal segments for graphic
        if (data.isEmpty() || dataType == DataType.NONE) return
        val max = data.maxBy(DisplayedWeatherItem::value)?.value ?: return
        val min = data.minBy(DisplayedWeatherItem::value)?.value ?: return
        val rows = when {
            max == min -> 1
            else -> max - min
        }
        val initialHeight = height.toFloat() - initialPadding * 2 - textSize * 2
        stepVertical = when (rows) {
            1 -> initialHeight
            else -> initialHeight / rows
        }
        stepHorizontal = width.toFloat() / data.size

        //init path reference point
        val firstItemHeight = when (rows) {
            1 -> when (dataType) {
                DataType.HOURLY_TEMP -> 0F
                DataType.PRECIPITATION -> stepVertical
                DataType.NONE -> return
            }
            else -> max * stepVertical - data.first().value * stepVertical
        }
        graphicPathCurrentY = initialPadding + textSize + firstItemHeight
        graphicPathCurrentX = 0F

        //draw time/temp for the first item
        val firstItem = data.first()
        drawTextInCenter(
            canvas, formatText(firstItem.value),
            graphicPathCurrentX, graphicPathCurrentY, textBottomPadding
        )
        drawTextInCenter(
            canvas, firstItem.time,
            graphicPathCurrentX, height.toFloat(), textBottomPadding
        )

        graphicPath.moveTo(graphicPathCurrentX, graphicPathCurrentY)
        data.forEachIndexed { index, weatherItem ->
            try {
                //draw horizontal line
                graphicPathCurrentX += stepHorizontal
                graphicPath.lineTo(graphicPathCurrentX, graphicPathCurrentY)

                //draw vertical line
                val segmentHeightDiff = (weatherItem.value - data[index + 1].value) * stepVertical
                graphicPathCurrentY += segmentHeightDiff
                graphicPath.lineTo(graphicPathCurrentX, graphicPathCurrentY)

                //draw time/temp for next item
                drawTextInCenter(
                    canvas, formatText(data[index + 1].value), graphicPathCurrentX, graphicPathCurrentY,
                    textBottomPadding
                )
                drawTextInCenter(
                    canvas, data[index + 1].time,
                    graphicPathCurrentX, height.toFloat(), textBottomPadding
                )
            } catch (e: Exception) {
            }
        }
        canvas.drawPath(graphicPath, paintGraphic)
    }

    private fun drawTextInCenter(
        canvas: Canvas, text: String,
        xStart: Float, yBottom: Float, bottomExtraPadding: Float = 0F
    ) {
        paintData.getTextBounds(text.toUpperCase(Locale.getDefault()), 0, text.length, tempTextBoundingRect)
        val x = stepHorizontal / 2F - tempTextBoundingRect.width() / 2F - tempTextBoundingRect.left
        val y = textSize / 2F - tempTextBoundingRect.height() / 2F
        canvas.drawText(text, xStart + x, yBottom - y - bottomExtraPadding, paintData)
    }

    private fun formatText(value: Int): String = when (dataType) {
        DataType.HOURLY_TEMP -> when {
            value > 0 -> "+$value"
            else -> value.toString()
        }
        DataType.PRECIPITATION -> context.getString(R.string.precipitation_unit_mm, value)
        DataType.NONE -> ""
    }

}