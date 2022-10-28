package com.insurance.assured.ui.welcome

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.insurance.assured.R
import com.insurance.assured.databinding.FragmentWelcomeBinding
import com.insurance.assured.di.datastore.AppConfigDataStore
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class WelcomeFragment : Fragment() {

    private lateinit var binding: FragmentWelcomeBinding
    private val viewModel: WelcomeViewModel by viewModels()

    @Inject
    lateinit var appConfigDataStore: AppConfigDataStore

    init {

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findNavController().navigate(WelcomeFragmentDirections.actionWelcomeFragmentToAuthorizedFragment())

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.action.collect { validUser ->
                    Log.d("Welcome", "Invoke")
                    if (validUser) {
                        findNavController().navigate(WelcomeFragmentDirections.actionWelcomeFragmentToPassCodeFragment())
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.checkUser()
        }


        binding.signUpButton.setOnClickListener {
            findNavController().navigate(R.id.signUpFragment)
        }
        binding.signInText.setOnClickListener {
            findNavController().navigate(R.id.signInFragment)
        }
    }
}