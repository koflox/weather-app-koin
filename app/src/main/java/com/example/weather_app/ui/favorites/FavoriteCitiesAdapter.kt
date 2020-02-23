package com.example.weather_app.ui.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_app.data.data.FavoriteCity
import com.example.weather_app.databinding.ItemFavoriteCityBinding
import com.example.weather_app.ui.base.BindableAdapter

class FavoriteCitiesAdapter(
    private val viewModel: FavoritesCitiesViewModel
) : RecyclerView.Adapter<FavoriteCityViewHolder>(), BindableAdapter<FavoriteCity> {

    val data = mutableListOf<FavoriteCity>()

    override fun setData(data: List<FavoriteCity>) {
        this.data.run {
            clear()
            addAll(data)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteCityViewHolder {
        val dataBinding = ItemFavoriteCityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteCityViewHolder(
            dataBinding
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: FavoriteCityViewHolder, position: Int) {
        holder.bind(viewModel, data[position])
    }

}

class FavoriteCityViewHolder(
    private val dataBinding: ItemFavoriteCityBinding
) : RecyclerView.ViewHolder(dataBinding.root) {

    fun bind(viewModel: FavoritesCitiesViewModel, city: FavoriteCity) {
        dataBinding.apply {
            this.viewModel = viewModel
            item = city
            executePendingBindings()
        }
    }

}



















