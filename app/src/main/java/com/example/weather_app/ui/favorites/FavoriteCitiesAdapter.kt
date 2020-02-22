package com.example.weather_app.ui.favorites

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_app.R
import com.example.weather_app.data.data.FavoriteCity
import kotlinx.android.synthetic.main.item_favorite_city.view.*

class FavoriteCitiesAdapter(
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<FavoriteCityViewHolder>() {

    interface OnItemClickListener {

        fun onCityClick(city: FavoriteCity, position: Int)

        fun onOptionsClick(city: FavoriteCity, position: Int)

    }

    val data = mutableListOf<FavoriteCity>()

    fun setData(cities: List<FavoriteCity>) {
        data.run {
            clear()
            addAll(cities)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteCityViewHolder {
        return FavoriteCityViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_favorite_city, parent, false),
            listener
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: FavoriteCityViewHolder, position: Int) {
        holder.bind(data[position], position)
    }

}

class FavoriteCityViewHolder(
    private val view: View,
    private val listener: FavoriteCitiesAdapter.OnItemClickListener
) : RecyclerView.ViewHolder(view) {

    @SuppressLint("SetTextI18n")
    fun bind(city: FavoriteCity, position: Int) {
        view.run {
            setOnClickListener {
                listener.onCityClick(city, position)
            }
            tvCityInfo.text = "${city.cityName}, ${city.country}"
            ibCityOptions.setOnClickListener { listener.onOptionsClick(city, position) }
        }
    }

}



















