package com.daferarevalo.espapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.daferarevalo.espapp.core.hide
import com.daferarevalo.espapp.core.show
import com.daferarevalo.espapp.databinding.ActivityBottomBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class BottomActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBottomBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_EspApp)
        super.onCreate(savedInstanceState)

        binding = ActivityBottomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_home, R.id.navigation_profile))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        observeDestinationChange()

        navController.addOnDestinationChangedListener{ controller, destination, arguments ->
            when(destination.id){
                R.id.loginFragment -> {
                    binding.navView.hide()
                }
                R.id.registerFragment -> {
                    binding.navView.hide()
                }
                R.id.channelsFragment -> {
                    binding.navView.hide()
                }
                R.id.editChannelFragment -> {
                    binding.navView.hide()
                }
                else -> {
                    binding.navView.show()
                }
            }
        }

    }

    private fun observeDestinationChange(){

    }
}