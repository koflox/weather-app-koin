package com.example.weather_app.ui.favorites

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_app.R
import com.example.weather_app.data.entity.FavoriteCity
import com.example.weather_app.extensions.loadFromUrl
import kotlinx.android.synthetic.main.item_favorite_city.view.*

class FavoriteCitiesAdapter(
        private val listener: OnItemClickListener
) : RecyclerView.Adapter<FavoriteCitiesAdapter.FavoriteCityViewHolder>() {

    interface OnItemClickListener {
        fun onOptionsClick(view: View, city: FavoriteCity)
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
        holder.bind(data[position])
    }

    class FavoriteCityViewHolder(
            private val view: View,
            private val listener: OnItemClickListener
    ) : RecyclerView.ViewHolder(view) {

        fun bind(city: FavoriteCity) {
            view.run {
                tvCityInfo.text = "${city.cityName}, ${city.country}" //todo
                ibCityOptions.setOnClickListener { listener.onOptionsClick(this, city) }
                ivCityPicture.loadFromUrl(city.imageUrl)
            }
        }

    }

}



















