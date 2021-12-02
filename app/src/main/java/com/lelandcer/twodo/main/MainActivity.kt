package com.lelandcer.twodo.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.lelandcer.twodo.R
import com.lelandcer.twodo.domain.CreatePlaceholderData
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Provider


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var fab: FloatingActionButton


    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_TwoDo)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab = findViewById(R.id.fab_main)
        enableUpNavigation()

    }

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

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment).navigateUp()
    }

}