package com.insurance.assured.ui.changeuserdata

import android.content.ContentValues
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.insurance.assured.databinding.FragmentChangeEmailBinding
import com.insurance.assured.ui.basefragments.BaseFragment

class ChangeEmailFragment : BaseFragment<FragmentChangeEmailBinding>(
    FragmentChangeEmailBinding::inflate
) {
    private val user = Firebase.auth.currentUser

    override fun init() {
    }

    override fun listener() {
        binding.saveButton.setOnClickListener {
            val email = binding.emailInput.text?.toString()

            if (!isValidEmail(email)) {
                binding.emailLayout.error = ("Incorrect Email")
            } else {
                user!!.updateEmail(email!!)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d(ContentValues.TAG, "User email address updated.")
                            Toast.makeText(
                                context,
                                "User Email address updated.",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            requireActivity().onBackPressed()

                        } else {
                            Toast.makeText(
                                context,
                                "Cant change Email, log out and try again",
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

    private fun isValidEmail(email: String?): Boolean {
        return if (email.isNullOrBlank()) {
            false
        } else {
            Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }
    }

    private fun updateErrorStates() = with(binding) {
        emailInput.doAfterTextChanged {
            emailLayout.error = null
        }
    }

}