package com.saket.weatherapp_withcoroutines.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.saket.weatherapp_withcoroutines.model.City
import com.saket.weatherapp_withcoroutines.repository.CityWeatherRepo
import java.util.function.Consumer

/**
Created by sshriwas on 2020-08-19

ViewModel survives some configuration changes of activity/fragment.
Hence it is useful to store data across say device rotations. It is
used alongwith livedata and data binding to be more effective.
 */
class CityWeatherViewModel(private val cityWeatherRepo: CityWeatherRepo) : ViewModel() {

    private val mCityList = ArrayList<City>()

    //Backing property for city list live data
    private val _liveCityList = MutableLiveData<List<City>>()

    val liveCityListData: LiveData<List<City>>
        get() = _liveCityList

    //Backing property for selected city liveData
    private val _selectedCity = MutableLiveData<City?>()

    val liveSelectedCity: LiveData<City?>
        get() = _selectedCity

    val liveShouldLoadData: LiveData<Boolean> = Transformations.map(_selectedCity) { city: City? ->
        previousSelectedCity == null && city == null
    }

    private var previousSelectedCity: City? = null

    init {
        _selectedCity.value = null
    }

    fun getCityWeatherInfo() {
        val cityList = listOf("London", "Gothenburg")
        cityList.forEach { cityName ->
            cityWeatherRepo.getCityInfo(cityName) { city ->
                run {
                    cityWeatherRepo.getCityWeatherInfo(city.woeid, Consumer { weatherInfo ->
                        run {
                            city.cityWeatherInfo = weatherInfo
                            mCityList.add(city)
                            _liveCityList.value = mCityList
                        }
                    })
                }
            }
        }
    }

    fun setSelectedCity(city: City?) {
        previousSelectedCity = _selectedCity.value
        _selectedCity.value = city
    }
}