package com.saket.weatherapp_withcoroutines.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.saket.weatherapp_withcoroutines.network.APIRetrofitInstance
import com.saket.weatherapp_withcoroutines.repository.CityWeatherRepo

/**
Created by sshriwas on 2020-10-17
 */

class CityWeatherViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CityWeatherViewModel::class.java)) {
            //Initialize CityWeatherRepo
            val weatherRetrofitInstance = APIRetrofitInstance.retrofitService
            val cityWeatherRepo = CityWeatherRepo(weatherRetrofitInstance)
            //Inject CityWeatherRepo to CityWeatherViewModel class constructor.
            return CityWeatherViewModel(cityWeatherRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}