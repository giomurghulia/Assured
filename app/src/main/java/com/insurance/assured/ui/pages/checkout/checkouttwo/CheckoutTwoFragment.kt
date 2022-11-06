package com.insurance.assured.ui.pages.checkout.checkouttwo

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.insurance.assured.common.enums.InsuranceCategory
import com.insurance.assured.databinding.FragmentCheckoutTwoBinding
import com.insurance.assured.ui.basefragments.BaseFragment
import com.insurance.assured.ui.sharedviewmodel.CheckoutViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CheckoutTwoFragment :
    BaseFragment<FragmentCheckoutTwoBinding>(FragmentCheckoutTwoBinding::inflate) {
    private val map: MutableMap<Int, String> = mutableMapOf()
    private val adapter: Adapter by lazy {
        Adapter { id, text ->
            map[id] = text
        }
    }
    private val sharedViewModel: CheckoutViewModel by activityViewModels()
    private val viewModel: CheckoutTwoViewModel by viewModels()
    override fun init() {
        bindRecycler()
        initRecyclerList()

    }

    override fun listener() {
        setOnBtnClickListener()
        setBackListener()
    }

    private fun setOnBtnClickListener(){
        binding.checkoutBtn.setOnClickListener {
            if (binding.buyerId.text.toString().length < 11) {
                toast("please enter valid id")
                return@setOnClickListener
            }
            if (sharedViewModel.checkOutState.value.insurancePacket!!.category == InsuranceCategory.HEALTH) {
                for (key in map.keys) {
                    if (map[key]!!.length < 11) {
                        toast("please enter valid ids")
                        return@setOnClickListener
                    }
                }
            } else {
                for (key in map.keys) {
                    if (map[key]!!.isEmpty()) {
                        toast("please enter valid input")
                        return@setOnClickListener
                    }
                }
            }
            sharedViewModel.onUserInfoInserted(binding.buyerId.text.toString(), map.values.toList())
            findNavController().navigate(CheckoutTwoFragmentDirections.actionCheckoutTwoFragmentToCheckoutOneFragment())
        }
    }
    private fun bindRecycler() {
        with(binding.recycler) {
            adapter = this@CheckoutTwoFragment.adapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun initRecyclerList() {
        val model = sharedViewModel.checkOutState.value.insurancePacket!!
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getInitialList(
                    model.category, try {
                        model.slogan.toInt()
                    } catch (e: Exception) {
                        1
                    }
                ).collect {
                    adapter.submitList(it)
                }

            }
        }
        binding.lable.text = when (model.category) {
            InsuranceCategory.HEALTH -> {
                if (model.slogan.toInt() == 1) {
                    "ID Of Beneficiary"
                } else {
                    "IDs Of Beneficiaries"
                }
            }
            InsuranceCategory.HOUSE -> "House Code"
            InsuranceCategory.VEHICLE -> "Vehicle Serial Number"
            else -> "Pet ID"
        }
    }

    private fun setBackListener(){
        binding.backImage.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }
}