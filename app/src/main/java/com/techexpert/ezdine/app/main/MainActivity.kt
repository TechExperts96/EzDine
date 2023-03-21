package com.techexpert.ezdine.app.main

import android.os.Bundle
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.techexpert.ezdine.app.R
import com.techexpert.ezdine.app.databinding.ActivityMainBinding
import com.techexpert.ezdine.app.foundation.base.BaseActivity
import com.techexpert.ezdine.app.foundation.base.UIEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity :
    BaseActivity<ActivityMainBinding, MainViewModel>(R.layout.activity_main, MainViewModel::class) {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun ActivityMainBinding.initialize() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        setSupportActionBar(binding.toolbar)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        appBarConfiguration =
            AppBarConfiguration.Builder()
                .setFallbackOnNavigateUpListener { onNavigateUp() }
                .build()
//        setupActionBarWithNavController(this, navController, appBarConfiguration)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    override fun onSupportNavigateUp(): Boolean {
        // Check if androidx.Navigation.ui navigateUp is imported and used
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private val onBackPressedCallback: OnBackPressedCallback =
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Log.e("handleOnBackPressed", "back pressed")
            }
        }

    override fun onUIEventTriggered(event: UIEvent) {
        when (event) {
        }
    }
}
