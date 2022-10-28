package com.insurance.assured.ui.authorized

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.insurance.assured.R
import com.insurance.assured.databinding.FragmentAuthorizedBinding

class AuthorizedFragment : Fragment() {
    private lateinit var binding: FragmentAuthorizedBinding

    private val viewModel: AuthorizedViewModel by viewModels()

    private lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = FragmentAuthorizedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.bottomNavigationView.background = null
        binding.bottomNavigationView.menu.getItem(2).isEnabled = false

        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.authorized_nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        setupWithNavController(binding.bottomNavigationView, navController)

        binding.homeButton.setOnClickListener {
            navController.navigate(R.id.action_global_homeFragment)
        }
    }
}