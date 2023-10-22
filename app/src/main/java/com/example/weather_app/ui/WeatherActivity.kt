package com.example.weather_app.ui

import android.view.View
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.weather_app.R
import com.google.android.material.navigation.NavigationView
import com.koflox.common_ui.base.BaseActivity

class WeatherActivity : BaseActivity() {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    private val drawerListener = object : DrawerLayout.SimpleDrawerListener() {
        override fun onDrawerOpened(drawerView: View) {
            // todo hide keyboard
            // todo https://proandroiddev.com/how-to-detect-if-the-android-keyboard-is-open-269b255a90f5
        }
    }

    override fun getLayoutId() = R.layout.activity_main

    override fun initViews() {
        setSupportActionBar(findViewById(R.id.toolbar))

        val topLevelDestinations = setOf(R.id.searchFragment, R.id.favoritesCitiesFragment, R.id.settingsFragment)
        navController = findNavController(R.id.fragmentHost)
        appBarConfiguration = AppBarConfiguration.Builder(topLevelDestinations)
            .setDrawerLayout(findViewById(R.id.drawerLayout))
            .build()
        setupActionBarWithNavController(navController, appBarConfiguration)
        findViewById<NavigationView>(R.id.navigationView).setupWithNavController(navController)
        handleNavigation()
    }

    override fun onStart() {
        super.onStart()
        findViewById<DrawerLayout>(R.id.drawerLayout).addDrawerListener(drawerListener)
    }

    override fun onStop() {
        super.onStop()
        findViewById<DrawerLayout>(R.id.drawerLayout).removeDrawerListener(drawerListener)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
            || super.onSupportNavigateUp()
    }

    private fun handleNavigation() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            supportActionBar?.subtitle = ""
            findViewById<DrawerLayout>(R.id.drawerLayout).setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            when (destination.id) {
                R.id.currentWeatherFragment -> {
                    findViewById<DrawerLayout>(R.id.drawerLayout).setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                }
            }
        }
    }

}
