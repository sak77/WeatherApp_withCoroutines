package com.saket.weatherapp_withcoroutines

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.saket.weatherapp_withcoroutines.model.WeatherInfo
import com.squareup.picasso.Picasso

/**
Created by sshriwas on 2020-10-24
 */

object BindingUtils {
    //Kotlin kapt plugin is used for annotation processing in kotlin. Used in case of Dagger or Data-binding
    //We use @JvmStatic annotation so that a corresponding static field is generated in the class file.
    //This is useful for java compiler to understand that this is a static function.
    @BindingAdapter(value = ["weatherIcon"])
    @JvmStatic fun setWeatherIcon(weatherIcon : ImageView, weatherInfo: WeatherInfo) {
        val abbr: String = weatherInfo.abbr
        val url =
            "https://www.metaweather.com/static/img/weather/png/64/$abbr.png"
        Picasso.get().load(url).into(weatherIcon)
    }
}