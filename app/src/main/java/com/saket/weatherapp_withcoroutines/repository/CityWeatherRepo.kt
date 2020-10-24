package com.saket.weatherapp_withcoroutines.repository

import com.saket.weatherapp_withcoroutines.model.City
import com.saket.weatherapp_withcoroutines.model.WeatherInfo
import com.saket.weatherapp_withcoroutines.network.WeatherAPIEndpoints
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.function.Consumer

/**
Created by sshriwas on 2020-08-14

Repository class is responsible for data operations.

 Note here i launch a coroutine via the async launcher and get the Deferred response via
 await() suspend function. The await will return the response value when the api execution is completed.
 */

class CityWeatherRepo(private val weatherRetrofitInstance : WeatherAPIEndpoints) {
    //Below creates a local Dependency on APIRetrofitInstance...so need to move it outside this class.
    //private val weatherRetrofitInstance by lazy { APIRetrofitInstance.retrofitService }

    //Here we pass a function as a parameter. The function is a consumer but we are not
    //explicitly saying it so. But for getCityWeatherInfo() we explicitly pass a Consumer.
    fun getCityInfo(cityName: String, cityUpdateCallback: (city: City) -> Unit) {

        CoroutineScope(Dispatchers.IO).launch {
            val result =
                weatherRetrofitInstance.getCityInfoAsync(cityName)    //returns deferred instance whose value will be set in future
            val cityList =
                result.await()   //Await is a suspend function that returns the result of async operation
            if (cityList.isNotEmpty()) {
                val city = cityList[0]
                cityUpdateCallback(city)
            }
        }
    }

    fun getCityWeatherInfo(
        woeid: String,
        weatherInfoUpdateCallback: Consumer<WeatherInfo>
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = weatherRetrofitInstance.getWeatherInfoAsync(woeid, "2020/10/25")
            //Log.v("CityWeatherRepo", "launching getCityWeatherInfo in ${Thread.currentThread().name}")
            val arrayOfWeatherInfos = result.await()
            if (arrayOfWeatherInfos.isNotEmpty()) {
                //Log.v("CityWeatherRepo", "got result for getCityWeatherInfo in ${Thread.currentThread().name}")
                val weatherInfo = arrayOfWeatherInfos[0]
                //Switching to main thread since we have to call the livedata.setValue() in the viewModel.
                //another option is to remain on IO but then use postValue() instead.
                withContext(Dispatchers.Main) {
                    //Log.v("CityWeatherRepo", "switch threads for getCityWeatherInfo to ${Thread.currentThread().name}")
                    weatherInfoUpdateCallback.accept(weatherInfo)
                }
            }
        }
    }
}