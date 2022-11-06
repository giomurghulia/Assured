package com.insurance.assured.ui.pages.checkout.checkoutone

import android.annotation.SuppressLint
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.insurance.assured.common.enums.InsuranceCategory
import com.insurance.assured.common.extensions.load
import com.insurance.assured.databinding.FragmentCheckoutOneBinding
import com.insurance.assured.ui.basefragments.BaseFragment
import com.insurance.assured.ui.sharedviewmodel.CheckoutViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat

@AndroidEntryPoint
class CheckoutOneFragment :
    BaseFragment<FragmentCheckoutOneBinding>(FragmentCheckoutOneBinding::inflate) {
    private val sharedViewModel: CheckoutViewModel by activityViewModels()

    @SuppressLint("SetTextI18n")
    override fun init() {
        val checkoutModel = sharedViewModel.checkOutState.value
        val model = checkoutModel.insurancePacket!!
        with(binding) {
            feats.text = model.feats.joinToString(", ")
            maxMoney.text = "$${model.totalMoney}"
            image.load(model.image)
            subtotal.text = "${model.monthlyPayment}$"
            total.text = "${model.monthlyPayment}$"
            money.text = "$${model.totalMoney}"
            userId.text = checkoutModel.userId.toString()
            tittle.text = model.title
            type.text = "${model.category.toString()} Insurance"
            val formatter = SimpleDateFormat.getDateInstance()
            startDate.text = formatter.format(System.currentTimeMillis()).toString()
            endDate.text =
                formatter.format(System.currentTimeMillis() + model.durationMonth.toLong() * 30 * 24 * 60 * 60 * 1000)
                    .toString()
            binding.detail.text =
                if (model.category == InsuranceCategory.HEALTH) "${model.slogan} person" else model.slogan


        }
    }

    override fun listener() {
        binding.checkoutBtn.setOnClickListener {
            findNavController().navigate(CheckoutOneFragmentDirections.actionCheckoutOneFragmentToLastCheckoutFragment())
        }
    }
}