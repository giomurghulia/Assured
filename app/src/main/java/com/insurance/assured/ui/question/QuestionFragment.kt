package com.insurance.assured.ui.question

import com.insurance.assured.databinding.FragmentQuestionBinding
import com.insurance.assured.ui.basefragments.BaseFragment

class QuestionFragment : BaseFragment<FragmentQuestionBinding>(
    FragmentQuestionBinding::inflate
) {
    override fun init() {
    }

    override fun listener() {
        binding.backImageImage.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }
}