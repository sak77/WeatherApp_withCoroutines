package com.saket.weatherapp_withcoroutines.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.saket.weatherapp_withcoroutines.databinding.FragmentWeatherDetailsBinding
import com.saket.weatherapp_withcoroutines.viewmodel.CityWeatherViewModel
import com.saket.weatherapp_withcoroutines.viewmodel.CityWeatherViewModelFactory

/**
Created by sshriwas on 2020-10-20
 */
class WeatherDetailsFragment : Fragment() {

    private val cityWeatherViewModel:CityWeatherViewModel by activityViewModels {
        CityWeatherViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentWeatherDetailsBinding.inflate(inflater, container, false)
        val city = cityWeatherViewModel.liveSelectedCity.value
        binding.city = city
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        //Reset selected city
        cityWeatherViewModel.setSelectedCity(null)
    }
}