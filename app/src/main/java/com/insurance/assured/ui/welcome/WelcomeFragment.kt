package com.insurance.assured.ui.welcome

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.insurance.assured.R
import com.insurance.assured.databinding.FragmentWelcomeBinding
import com.insurance.assured.ui.basefragments.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WelcomeFragment : BaseFragment<FragmentWelcomeBinding>(
    FragmentWelcomeBinding::inflate
) {
    private val viewModel: WelcomeViewModel by viewModels()

    override fun init() {
//        findNavController().navigate(WelcomeFragmentDirections.actionWelcomeFragmentToAuthorizedFragment())
        viewModel.checkUser()
    }

    override fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.action.collect { validUser ->
                    Log.d("Welcome", "Invoke")
                    if (validUser) {
                        findNavController().navigate(WelcomeFragmentDirections.actionWelcomeFragmentToPassCodeFragment())
                    }
                }
            }
        }
    }

    override fun listener() {

        binding.signUpButton.setOnClickListener {
            findNavController().navigate(R.id.signUpFragment)
        }
        binding.signInText.setOnClickListener {
            findNavController().navigate(R.id.signInFragment)
        }

    }
}