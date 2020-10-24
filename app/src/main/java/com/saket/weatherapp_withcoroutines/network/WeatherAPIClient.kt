package com.saket.weatherapp_withcoroutines.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.saket.weatherapp_withcoroutines.model.City
import com.saket.weatherapp_withcoroutines.model.WeatherInfo
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.*

/**
Created by sshriwas on 2020-08-14

When using coroutines with retrofit, the methods in the API interface return an instance of
 Deferred instead of Call. So to handle this we must first add a CoroutineCallAdapterFactory when
 initializing the retrofitInstance. Then all interface methods can return Deferred instead of Call.
 In the repository class we launch a coroutine via async launcher, and use await() to get hold of the
 deferred response object.

 */

//Moshi instance with kotlinJsonAdapterFactory
private val moshi: Moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

//Retrofit instance
private val retrofitInstance =
    Retrofit.Builder().baseUrl("https://www.metaweather.com/api/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())   //Allows retrofit to return Deferred instead of Call
        .build()

//Interface that exposes API endpoints
interface WeatherAPIEndpoints {
    @GET("location/search/")
    //fun getCityInfo(@Query("query") cityName: String) : Call<Array<City>>

    //NOTE: When using Coroutines with Retrofit, we return a deferred instance instead of
    //Call. Also method name ends with async.
    fun getCityInfoAsync(@Query("query") cityName: String): Deferred<Array<City>>

    @GET("location/{woeid}/{date}/")
    fun getWeatherInfoAsync(@Path("woeid") woeid: String, @Path("date") date: String) : Deferred<Array<WeatherInfo>>
}

//object represents a single static instance. Like a singleton class, it can have only one instance.
//On the other hand, companion object represents static member of a class.
//Use @JvmStatic if you wish to generate a static keyword in the class file for this class...
//You can see this in the bindingUtils class.
object APIRetrofitInstance {
    //Lazy initialization
    val retrofitService: WeatherAPIEndpoints by lazy {
        retrofitInstance.create(WeatherAPIEndpoints::class.java)
    }
}
