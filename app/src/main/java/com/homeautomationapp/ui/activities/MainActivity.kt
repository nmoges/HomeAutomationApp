package com.homeautomationapp.ui.activities

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.homeautomationapp.AppConstants
import com.homeautomationapp.R
import com.homeautomationapp.databinding.ActivityMainBinding
import com.homeautomationapp.ui.fragments.HeatersFragment
import com.homeautomationapp.ui.fragments.LightsFragment
import com.homeautomationapp.ui.fragments.RollerShuttersFragment
import com.homeautomationapp.ui.fragments.UserProfileFragment
import com.homeautomationapp.viewmodels.ViewModelData
import dagger.hilt.android.AndroidEntryPoint

/**
 * Application main activity.
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    lateinit var navController: NavController

    private lateinit var navHostFragment: NavHostFragment

    lateinit var viewModel: ViewModelData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeToolbar()
        initializeNavigation()
        initializeViewModel()
        viewModel.initializeDB(applicationContext)
    }

    private fun initializeNavigation() {
        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_frg)
                          as NavHostFragment
        navController = navHostFragment.navController
    }

    private fun initializeViewModel() {
        viewModel = ViewModelProvider(this)[ViewModelData::class.java]
    }

    @Suppress("DEPRECATION")
    private fun initializeToolbar() {
        setSupportActionBar(binding.toolbar)
        val color =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                resources.getColor(R.color.white, null)
            else
                resources.getColor(R.color.white)
        binding.toolbar.setTitleTextColor(color)
    }

    fun setToolbarProperties(title: String, backIconDisplay: Boolean) {
        supportActionBar?.apply {
            setTitle(title)
            setDisplayHomeAsUpEnabled(backIconDisplay)
            if (backIconDisplay) {
                setHomeAsUpIndicator(ResourcesCompat.getDrawable(resources,
                    R.drawable.ic_baseline_arrow_back_24dp_white, null))
            }
        }
    }

    fun displayFragment(tag: String) {
        when(tag) {
            AppConstants.TAG_FRAGMENT_USER_PROFILE ->  {
                navController.navigate(R.id.action_frg_list_devices_to_frg_user_profile)
            }
            AppConstants.TAG_FRAGMENT_HEATERS -> {
                // TODO : not implemented yet
            }
            AppConstants.TAG_FRAGMENT_LIGHTS -> {
                // TODO : not implemented yet
            }
            AppConstants.TAG_FRAGMENT_ROLLER_SHUTTERS -> {
                // TODO : not implemented yet
            }
            AppConstants.TAG_FRAGMENT_DEVICES_MANAGER -> {
                navController.navigate(R.id.action_frg_list_devices_to_frg_devices_manager)
            }
        }
    }

    override fun onBackPressed() {
        navController.currentDestination?.let { navDestination ->
            when(navDestination.label.toString()) {
                resources.getString(R.string.frg_user_profile_label) -> {
                    supportFragmentManager.primaryNavigationFragment
                                          ?.childFragmentManager?.fragments?.get(0)?.let {
                        (it as UserProfileFragment).displayCancellationDialog(this,
                                                                              supportFragmentManager)
                    }
                }
                resources.getString(R.string.frg_lights_label) -> {
                    supportFragmentManager.primaryNavigationFragment
                                          ?.childFragmentManager?.fragments?.get(0)?.let {
                        (it as LightsFragment).displayCancellationDialog(this,
                                                                         supportFragmentManager)
                    }
                }
                resources.getString(R.string.frg_roller_shutters_label) -> {
                    supportFragmentManager.primaryNavigationFragment
                                          ?.childFragmentManager?.fragments?.get(0)?.let {
                        (it as RollerShuttersFragment).displayCancellationDialog(this,
                                                                                 supportFragmentManager)
                    }
                }
                resources.getString(R.string.frg_heaters_label) -> {
                    supportFragmentManager.primaryNavigationFragment
                                          ?.childFragmentManager?.fragments?.get(0)?.let {
                        (it as HeatersFragment).displayCancellationDialog(this,
                                                                          supportFragmentManager)
                    }
                }
                else -> {
                    super.onBackPressed()
                }
            }
        }
    }

    fun removeFragmentFromBackStack() {
        navController.popBackStack()
    }
}