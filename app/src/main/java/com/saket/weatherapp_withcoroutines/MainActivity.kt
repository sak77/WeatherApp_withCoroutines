package com.saket.weatherapp_withcoroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.saket.weatherapp_withcoroutines.databinding.ActivityMainBinding
import com.saket.weatherapp_withcoroutines.ui.CityListFragment

/**
 * Date started - 14 August 2020.
 * Date completed - 24 October 2020.
 * Purpose of this app is to have a clean implementation of the Weather app with Coroutines.
 * Functional programming - Yes
 * Rxjava2 - No
 * Retrofit - Yes
 * Kotlin Coroutines - Yes
 * Viewbinding - Yes
 * Databinding & Binding-Adapter - Yes
 * Binding adapter - Yes
 * Navigation graph - No
 * MVVM pattern - Yes
 * LiveData & ViewModel- Yes
 * Repository - Yes
 * Room DB - No
 * ListAdapter for Recyclerview - Yes
 * WorkManager API - No
 * Guidelines in layout - Yes
 * <p>
 * MainActivity Learnings -
 */
class MainActivity : AppCompatActivity() , FragmentManager.OnBackStackChangedListener {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //Setup toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "My Title"
        supportFragmentManager.addOnBackStackChangedListener { onBackStackChanged() }
        binding.toolbar.setNavigationIcon(android.R.drawable.ic_menu_mylocation)
        binding.toolbar.setNavigationOnClickListener {
            if (supportFragmentManager.backStackEntryCount > 0) {
                supportFragmentManager.popBackStack()
            }
        }

        //Load CityList fragment
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                add(R.id.fragment_container, CityListFragment())
            }
        }
    }

    override fun onBackStackChanged() {
        //Get child count in backstack, if it is greater than one then change icon
        if (supportFragmentManager.backStackEntryCount == 0) {
            binding.toolbar.setNavigationIcon(android.R.drawable.ic_menu_mylocation)
        } else {
            binding.toolbar.setNavigationIcon(android.R.drawable.ic_media_previous)
        }
    }

}