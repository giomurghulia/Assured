package com.insurance.assured.ui.pages.cashless

import com.insurance.assured.databinding.FragmentCashlessBinding
import com.insurance.assured.ui.basefragments.BaseFragment


class CashlessFragment : BaseFragment<FragmentCashlessBinding>(
    FragmentCashlessBinding::inflate
) {

    override fun init() {
    }

    override fun observe() {
        binding.backImage.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun listener() {

    }

}