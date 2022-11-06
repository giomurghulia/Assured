package com.insurance.assured.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.insurance.assured.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.insurance.assured.R
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    companion object {
        private var bul: Boolean = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                false
            }
        }

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.bottomNavigationView.background = null
        binding.bottomNavigationView.menu.getItem(2).isEnabled = false

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)

        binding.homeButton.setOnClickListener {

            if (binding.bottomNavigationView.selectedItemId != R.id.homeFragment)
                onBackPressed()
            //navController.navigate(R.id.homeFragment)
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.cashlessFragment, R.id.signInFragment, R.id.signUpFragment, R.id.notSignedInFragment, R.id.checkoutTwoFragment, R.id.checkoutOneFragment -> {
                    binding.bottomAppBar.visibility = View.GONE
                    binding.homeButton.visibility = View.GONE
                }
                R.id.policyItemFragment -> {
                    binding.bottomAppBar.visibility = View.GONE
                    binding.homeButton.visibility = View.GONE
                }
                R.id.questionFragment -> {
                    binding.bottomAppBar.visibility = View.GONE
                    binding.homeButton.visibility = View.GONE
                }
                else -> {
                    binding.bottomAppBar.visibility = View.VISIBLE
                    binding.homeButton.visibility = View.VISIBLE
                }
            }
        }

    }
}