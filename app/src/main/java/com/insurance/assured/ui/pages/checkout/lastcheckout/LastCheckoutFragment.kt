package com.insurance.assured.ui.pages.checkout.lastcheckout

import android.annotation.SuppressLint
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.insurance.assured.common.extensions.load
import com.insurance.assured.databinding.FragmentLastCheckoutBinding
import com.insurance.assured.ui.basefragments.BaseFragment
import com.insurance.assured.ui.sharedviewmodel.CheckoutViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LastCheckoutFragment :
    BaseFragment<FragmentLastCheckoutBinding>(FragmentLastCheckoutBinding::inflate) {

    private val sharedViewModel: CheckoutViewModel by activityViewModels()

    @SuppressLint("SetTextI18n")
    override fun init() {
        val model = sharedViewModel.checkOutState.value.insurancePacket!!
        with(binding) {
            title.text = model.title
            maxMoney.text = "$${model.totalMoney}"
            priceTotal.text = "${(model.monthlyPayment * model.durationMonth)}$"
            image.load(model.image)
            monthlyPayment.text = model.monthlyPayment.toString() + "$"
        }
    }

    override fun listener() {
        binding.checkoutBtn.setOnClickListener {
            sharedViewModel.onCheckout()
            toast("purchasing")
            it.isClickable = false
        }
        binding.backImage.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                sharedViewModel.purchaseSuccessSharedFlow.collectLatest {
                    if (it) {
                        toast("purchase was successful!")
                        sharedViewModel.deleteUnfinishedCheckout()
                        findNavController().navigate(LastCheckoutFragmentDirections.actionLastCheckoutFragmentToHomeFragment())
                    } else {
                        binding.checkoutBtn.isClickable = true
                        toast("there was a problem purchase was not successful")
                    }
                }
            }
        }
    }


}