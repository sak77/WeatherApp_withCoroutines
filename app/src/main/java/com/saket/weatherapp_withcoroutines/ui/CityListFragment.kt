package com.saket.weatherapp_withcoroutines.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.*
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import com.saket.weatherapp_withcoroutines.R
import com.saket.weatherapp_withcoroutines.databinding.FragmentCitylistBinding
import com.saket.weatherapp_withcoroutines.viewmodel.CityWeatherViewModel
import com.saket.weatherapp_withcoroutines.viewmodel.CityWeatherViewModelFactory
import java.util.function.Consumer

/**
Created by sshriwas on 2020-08-14
 */
class CityListFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentCitylistBinding.inflate(inflater, container, false)
        //Using Kotlin property delegate to initialize viewmodel
        val cityWeatherViewModel: CityWeatherViewModel by activityViewModels {
            CityWeatherViewModelFactory()
        }

        cityWeatherViewModel.liveSelectedCity.observe(viewLifecycleOwner, Observer { city ->
            run {
                if (city != null) {
                    parentFragmentManager.commit {
                        replace(R.id.fragment_container, WeatherDetailsFragment())
                        addToBackStack(null)
                    }
                }
            }
        })

        val cityListAdapter = CityListAdapter(Consumer { city ->
            run {
                //Navigate to details fragment.
                cityWeatherViewModel.setSelectedCity(city)
            }
        })

        //Set list adapter
        binding.cityList.adapter = cityListAdapter
        //Add observer for city list live data
        cityWeatherViewModel.liveCityListData.observe(viewLifecycleOwner, Observer { city_list ->
            run {
                cityListAdapter.submitList(city_list.toList())
            }
        })

        //This ensures data is not re-loaded when user returns from weather details page...
        cityWeatherViewModel.liveShouldLoadData.observe(viewLifecycleOwner) {shouldLoadData ->
            if (shouldLoadData) {
                run {
                    //this ensures the list is not loaded when device rotates..
                    if (savedInstanceState == null) cityWeatherViewModel.getCityWeatherInfo()
                }
            }
        }

        return binding.root
    }
}