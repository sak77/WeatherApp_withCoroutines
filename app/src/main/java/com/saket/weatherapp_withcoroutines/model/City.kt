package com.saket.weatherapp_withcoroutines.model

import com.squareup.moshi.Json

/**
Created by sshriwas on 2020-08-14

Data class must define its primary members in the constructors, these will be used to compare instances
of the data class. Fields defined outside the constructor will not be used for comparision purpose.
 */

data class City(@Json(name = "title") val cityName: String, val woeid: String) {
    lateinit var cityWeatherInfo: WeatherInfo
}