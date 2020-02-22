package com.example.weather_app.ui.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_app.util.notNull


@Suppress("MemberVisibilityCanBePrivate")
@SuppressLint("Recycle")
class RecyclerViewEmptySupport @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    private val observer: AdapterDataObserver = object : AdapterDataObserver() {
        override fun onChanged() {
            super.onChanged()
            checkIfEmpty()
        }
    }

    var emptyView: View? = null
        set(value) {
            field = value
            checkIfEmpty()
        }

    fun checkIfEmpty() {
        notNull(emptyView, adapter) { emptyView, adapter ->
            emptyView.visibility = if (adapter.itemCount > 0) View.GONE else View.VISIBLE
        }
    }

    override fun setAdapter(adapter: Adapter<*>?) {
        getAdapter()?.unregisterAdapterDataObserver(observer)
        super.setAdapter(adapter)
        adapter?.registerAdapterDataObserver(observer)
    }

}