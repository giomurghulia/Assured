package com.insurance.assured.ui.changeuserdata

import android.content.ContentValues
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.insurance.assured.databinding.FragmentChangePasswordBinding
import com.insurance.assured.ui.basefragments.BaseFragment


class ChangePasswordFragment : BaseFragment<FragmentChangePasswordBinding>(
    FragmentChangePasswordBinding::inflate
) {
    private val user = Firebase.auth.currentUser

    override fun init() {
    }

    override fun listener() {
        binding.saveButton.setOnClickListener {
            val password = binding.passInput.text?.toString()
            if (!(password != null && password.isNotBlank() && password.length > 6)) {
                binding.passLayout.error = ("Incorrect Password")
            } else {
                user!!.updatePassword(password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d(ContentValues.TAG, "User password updated.")
                            Toast.makeText(
                                context,
                                "User Password updated.",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            requireActivity().onBackPressed()
                        } else {
                            Toast.makeText(
                                context,
                                "Cant change Password, log out and try again",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }
            }
        }

        binding.backImage.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun observe() {
        updateErrorStates()
    }

    private fun updateErrorStates() = with(binding) {
        passInput.doAfterTextChanged {
            passLayout.error = null
        }
    }
}