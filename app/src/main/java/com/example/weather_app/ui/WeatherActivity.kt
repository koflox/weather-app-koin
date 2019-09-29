package com.example.weather_app.ui

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.weather_app.R
import com.example.weather_app.extensions.setupWithNavController
import com.example.weather_app.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class WeatherActivity : BaseActivity() {

    private var currentNavController: LiveData<NavController>? = null

    override fun getLayoutId() = R.layout.activity_main

    override fun afterCreate(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        setupBottomNavigationBar()
    }

    override fun initViews() {
        setSupportActionBar(toolbar)
    }

    private fun setupBottomNavigationBar() {
        val navGraphIds = listOf(R.navigation.weather, R.navigation.favorites, R.navigation.settings)
        val controller = bottomNavMain.setupWithNavController(
                navGraphIds = navGraphIds,
                fragmentManager = supportFragmentManager,
                containerId = R.id.nav_host_container,
                intent = intent
        )
        controller.observe(this, Observer { navController ->
            setupActionBarWithNavController(navController)
        })
        currentNavController = controller
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }



}
