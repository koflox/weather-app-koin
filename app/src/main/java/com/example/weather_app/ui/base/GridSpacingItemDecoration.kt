package com.example.weather_app.ui.base

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GridSpacingItemDecoration(
        private val spanCount: Int,
        private val spacing: Int,
        private var applyForTop: Boolean = false,
        private var applyForBottom: Boolean = false
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        val column = position % spanCount
        outRect.apply {
            left = column * spacing / spanCount
            right = spacing - (column + 1) * spacing / spanCount
            if (applyForTop) top = spacing
            if (applyForBottom) bottom = spacing
        }
    }
}