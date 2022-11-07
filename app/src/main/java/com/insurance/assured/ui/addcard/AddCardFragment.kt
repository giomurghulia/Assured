package com.insurance.assured.ui.addcard

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.insurance.assured.databinding.FragmentAddCardBinding
import com.insurance.assured.ui.basefragments.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.UUID.randomUUID

@AndroidEntryPoint
class AddCardFragment : BaseFragment<FragmentAddCardBinding>(
    FragmentAddCardBinding::inflate
) {
    private val viewModel: AddCardViewModel by viewModels()
    private val currentUser get() = Firebase.auth.currentUser


    override fun init() {
    }

    override fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    if (it == true) {
                        Toast.makeText(context, "success add card", Toast.LENGTH_LONG)
                            .show()

                        requireActivity().onBackPressed()
                    } else if (it == false) {
                        Toast.makeText(context, "Problem Add to card", Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }
        }
    }

    override fun listener() {
        binding.saveButton.setOnClickListener {
            if (submitForm()) {
                saveCard()
            } else {
                Toast.makeText(context, "invalid Card", Toast.LENGTH_LONG)
                    .show()
            }
        }
        binding.backImage.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun submitForm(): Boolean = with(binding) {
        val cardNum = cardNumEditText.text?.toString()
        val month = mmEditText.text?.toString()
        val year = yyEditText.text?.toString()
        val cvc = cvcEditText.text?.toString()

        var isFormValid = true

        if (cardNum.isNullOrEmpty() && cardNum?.length != 16) {
            isFormValid = false
        }
        if (month.isNullOrEmpty() && cardNum?.length != 2) {
            isFormValid = false
        }
        if (year.isNullOrEmpty() && cardNum?.length != 2) {
            isFormValid = false
        }
        if (cvc.isNullOrEmpty() && cardNum?.length != 3) {
            isFormValid = false
        }

        return isFormValid
    }

    private fun saveCard() = with(binding) {
        val cardNum = cardNumEditText.text?.toString()

        viewModel.insertCard(
            currentUser?.email!!,
            randomUUID().toString(),
            cardNum!!.takeLast(4)
        )
    }
}