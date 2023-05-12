package com.example.coderbytechallange.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.coderbytechallange.R
import com.example.coderbytechallange.databinding.ActivityMainBinding

class MainActivity: AppCompatActivity() {
    private var navController: NavController? = null
    private var binding: ActivityMainBinding? = null
    private var appBarConfiguration: AppBarConfiguration? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        navController = findNavController(R.id.navHostFragment)
        setUpActionBar()
    }
    private fun setUpActionBar() {
        navController?.let {
            NavigationUI.setupActionBarWithNavController(this, it)
        }
    }

    override fun onSupportNavigateUp(): Boolean = navController?.let { nav ->
        appBarConfiguration.takeIf { it != null }?.let {
            nav.navigateUp(it)
        } ?: nav.navigateUp()
    } ?: false
}