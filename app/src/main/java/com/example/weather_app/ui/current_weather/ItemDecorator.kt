package com.example.weather_app.ui.current_weather

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ItemDecorator(
        private val spacing: Int,
        private val spanCount: Int = 0
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View,
                                parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        val column = position % spanCount

        outRect.apply {
            left = column * spacing / spanCount
            right = spacing - (column + 1) * spacing / spanCount
            if (position >= spanCount) {
                top = spacing
            }
        }
    }

}