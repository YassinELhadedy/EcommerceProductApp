package com.jumia.myapplication.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavDirections
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.jumia.myapplication.R
import com.jumia.myapplication.databinding.ActivityMainBinding
import com.jumia.myapplication.ui.NavigationJourney.PRODUCTDETAIL
import com.jumia.myapplication.ui.NavigationJourney.STORY_DETAIL
import com.jumia.myapplication.ui.home.HomeFragmentDirections
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_JumiaApp)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        // Hook your navigation controller to bottom navigation view
        binding.bottomNavigation.setupWithNavController(navController)
        AppCenter.start(
            application, "4accee15-1036-41cb-ac1f-05f44c30ae07",
            Analytics::class.java, Crashes::class.java
        )

        observer()
    }

    private fun observer() {
        mainViewModel.navigationJourney.observe(this) {
            it?.let {
                when (it.peekContent().navigationJourney) {
                    STORY_DETAIL -> openFragment(HomeFragmentDirections.actionHomeFragmentToStoryDetailFragment())
                    PRODUCTDETAIL -> openFragment(HomeFragmentDirections.actionHomeFragmentToProductDetailFragment(it.peekContent().bundle?.getString("SKU")?.toInt()!!))
                }
            }
        }
    }

    private fun openFragment(navigationDirection: NavDirections) {
        navigationDirection.apply {
            findNavController(this@MainActivity, R.id.nav_host_fragment).navigate(this)
        }
    }
}