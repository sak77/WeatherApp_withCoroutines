package com.saket.weatherapp_withcoroutines.model

import com.squareup.moshi.Json

/**
Created by sshriwas on 2020-08-14

 All primary fields go into the constructor of the data class.
 Secondary fields can be included in the class body.
 Primary fields are used for comparision purpose via equals() method.

 @Json() is an annotation for Moshi which provides the name to be used while parsing the json response.
 */
data class WeatherInfo(@Json(name = "weather_state_name") val weatherName: String,
                       @Json(name = "min_temp") val minTemp: String,
                       @Json(name = "max_temp") val maxTemp: String,
                       @Json(name = "wind_speed") val windspeed: String) {
    @Json(name = "weather_state_abbr") var abbr = String()
}