package com.saket.weatherapp_withcoroutines.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.saket.weatherapp_withcoroutines.R
import com.saket.weatherapp_withcoroutines.databinding.CitylistListitemBinding
import com.saket.weatherapp_withcoroutines.model.City
import java.util.function.Consumer

/**
Created by sshriwas on 2020-10-17

ListAdapter is an upgraded version of recyclerview adapter since it allows to
selectively update only parts of the list that has changed.
When initializing the adapter we need to define a viewholder and a DiffUtil.ItemCallback as well.

 */
class CityListAdapter(private val onCityClickListener: Consumer<City>) :
    ListAdapter<City, CityListAdapter.CityViewholder>(MyItemCallback()) {

    /*
    The ListAdapter is just like a shell. It does not hold any logic about which data to show, or
    how to show the data. All that logic is part of the ViewHolder class.
     */
    class CityViewholder(val binding: CitylistListitemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(city: City, cityClickListener: Consumer<City>) {
            binding.city = city
            binding.root.setOnClickListener {
                cityClickListener.accept(city)
            }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): CityViewholder {
                val cityListItemBinding = CitylistListitemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return CityViewholder(cityListItemBinding)
            }
        }
    }

    /*
    Compares the recycler view items.
    If items are different then it will selectively update those items...

    areItemsTheSame - compares items using item id.
    areContentsTheSame - compares entire objects.
     */
    class MyItemCallback : DiffUtil.ItemCallback<City>() {
        override fun areItemsTheSame(oldItem: City, newItem: City): Boolean {
            return oldItem.woeid == newItem.woeid
        }

        override fun areContentsTheSame(oldItem: City, newItem: City): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewholder {
        //val root = LayoutInflater.from(parent.context).inflate(R.layout.citylist_listitem,parent,false)
        return CityViewholder.from(parent)
    }

    override fun onBindViewHolder(holder: CityViewholder, position: Int) {
        val city = getItem(position)
        holder.bind(city, cityClickListener = onCityClickListener)
    }
}