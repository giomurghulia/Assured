package com.insurance.assured.ui.authorized

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.insurance.assured.R
import com.insurance.assured.databinding.FragmentAuthorizedBinding
import com.insurance.assured.databinding.FragmentHomeBinding
import com.insurance.assured.ui.basefragments.BaseFragment
import com.insurance.assured.ui.welcome.WelcomeFragmentDirections
import kotlinx.coroutines.launch

class AuthorizedFragment : BaseFragment<FragmentAuthorizedBinding>(
    FragmentAuthorizedBinding::inflate
) {
    private val viewModel: AuthorizedViewModel by viewModels()

    private lateinit var navController: NavController

    override fun init() {

        binding.bottomNavigationView.background = null
        binding.bottomNavigationView.menu.getItem(2).isEnabled = false

        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.authorized_nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        setupWithNavController(binding.bottomNavigationView, navController)
    }

    override fun listener() {
        binding.homeButton.setOnClickListener {
            navController.navigate(R.id.action_global_homeFragment)
        }
        binding.profileButton.setOnClickListener {
            navController.navigate(R.id.action_global_profileFragment)
        }
    }

    override fun observe() {

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.action.collect { validUser ->
                    if (!validUser) {
                        findNavController().navigate(R.id.action_global_welcomeFragment)
                    }
                }
            }
        }
    }
}