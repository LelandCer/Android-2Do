package com.lelandcer.twodo.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.lelandcer.twodo.R
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), FabActivity {

    override lateinit var fab: FloatingActionButton


    override fun onCreate(savedInstanceState: Bundle?) {
        // Hide the splash screen, show our actual app theme
        setTheme(R.style.Theme_TwoDo)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Assign the FAB
        fab = findViewById(R.id.fab_main)
        enableUpNavigation()

    }

    // Sets up the navController to show the appBar back button when not on the home fragment
    private fun enableUpNavigation() {
        val hostFragment = supportFragmentManager.findFragmentById(
            R.id.nav_host_fragment
        )
        val nc = hostFragment?.findNavController()

        nc?.let {
            val appBarConfiguration = AppBarConfiguration.Builder(nc.graph).build()
            NavigationUI.setupActionBarWithNavController(this, it, appBarConfiguration)
        }
    }

    // Use the navController for Up navigation
    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment).navigateUp()
    }

}