package com.insurance.assured.ui.pages.checkout.lastcheckout

import android.annotation.SuppressLint
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.insurance.assured.common.extensions.load
import com.insurance.assured.databinding.FragmentLastCheckoutBinding
import com.insurance.assured.domain.usecases.checkoutsusecase.CheckConnectionUseCase
import com.insurance.assured.ui.basefragments.BaseFragment
import com.insurance.assured.ui.sharedviewmodel.CheckoutViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LastCheckoutFragment :
    BaseFragment<FragmentLastCheckoutBinding>(FragmentLastCheckoutBinding::inflate) {

    private val sharedViewModel: CheckoutViewModel by activityViewModels()
    private var clickable = true
    private val viewModel: LastCheckoutViewModel by viewModels()
    private val adapter: Adapter by lazy {
        Adapter {
            viewModel.onCheck(it)
            cardIsChecked = true
        }
    }
    private var cardIsChecked = false
    @SuppressLint("SetTextI18n")
    override fun init() {
        viewModel.getCards()
        initRecycler()
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
       binding.addCard.setOnClickListener {
           findNavController().navigate(LastCheckoutFragmentDirections.actionGlobalAddCardFragment())
       }
        binding.checkoutBtn.setOnClickListener {
            if(!cardIsChecked){
                toast("please choose payment method")
                return@setOnClickListener
            }
            if (clickable) {
                clickable = false
                sharedViewModel.onCheckout()
                toast("purchasing")
            }
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
                        clickable = true
                        binding.checkoutBtn.isClickable = true
                        toast("there was a problem purchase was not successful")
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.cardStateFlow.collect {
                    adapter.submitList(it)
                }
            }
        }
    }

    private fun initRecycler() {
        binding.recycler.adapter = adapter
        binding.recycler.layoutManager = LinearLayoutManager(context)
    }

}