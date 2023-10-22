package com.koflox.common_ui.base

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class UniversalItemDecorator(
    private val spacing: Int,
    private val type: Type,
    private val gridSpanCount: Int? = null
) : RecyclerView.ItemDecoration() {

    enum class Type {
        GRID,
        VERTICAL
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        when (type) {
            Type.GRID -> {
                gridSpanCount ?: return
                val column = position % gridSpanCount
                outRect.apply {
                    left = column * spacing / gridSpanCount
                    right = spacing - (column + 1) * spacing / gridSpanCount
                    if (position >= gridSpanCount) {
                        top = spacing
                    }
                }
            }
            Type.VERTICAL -> {
                val itemCount = parent.adapter?.itemCount ?: return
                val bottomSpacing = when (position) {
                    itemCount -> 0
                    else -> spacing
                }
                outRect.bottom = bottomSpacing
            }
        }
    }
}
