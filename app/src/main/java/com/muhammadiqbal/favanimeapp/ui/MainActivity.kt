package com.muhammadiqbal.favanimeapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.muhammadiqbal.favanimeapp.R

class MainActivity : AppCompatActivity() {
    private lateinit var navigationController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigationController = findNavController(R.id.navHostFragment)
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.topFragment, R.id.searchedFragment, R.id.favoriteFragment)
        )

        NavigationUI.setupActionBarWithNavController(this, navigationController, appBarConfiguration)

        val bottomBarNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomBarNavigation.setupWithNavController(navigationController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navigationController.navigateUp()
    }
}
