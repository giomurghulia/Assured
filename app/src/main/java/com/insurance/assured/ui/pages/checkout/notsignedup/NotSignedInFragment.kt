package com.insurance.assured.ui.pages.checkout.notsignedup

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.insurance.assured.databinding.FragmentNotSignedInBinding
import com.insurance.assured.ui.basefragments.BaseFragment
import com.insurance.assured.ui.sharedviewmodel.CheckoutViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NotSignedInFragment :
    BaseFragment<FragmentNotSignedInBinding>(FragmentNotSignedInBinding::inflate) {
    private val sharedViewModel: CheckoutViewModel by activityViewModels()

    override fun init() {
        if (Firebase.auth.currentUser != null) {
            sharedViewModel.signedAndBack = true
            requireActivity().onBackPressed()
        }
    }

    override fun listener() {
        binding.cancelButton.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.backImage.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.signIpnButton.setOnClickListener {
            findNavController().navigate(NotSignedInFragmentDirections.actionGlobalSignInFragment())
        }
    }

}