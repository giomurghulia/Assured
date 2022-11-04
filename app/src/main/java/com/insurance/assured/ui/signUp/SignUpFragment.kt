package com.insurance.assured.ui.signUp

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.insurance.assured.databinding.FragmentSignUpBinding
import kotlinx.coroutines.launch


class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding

    private val viewModel: SignUpViewModel by viewModels()

    private val auth = Firebase.auth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateErrorStates()

        binding.signUpButton.setOnClickListener {
            if (submitForm()) {
                val email = binding.emailInput.text.toString()
                val password = binding.passInput.text.toString()

                register(email, password)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    when (it) {
                        is Resource.Success -> {
                            Toast.makeText(
                                context,
                                "signInWithEmail:success",
                                Toast.LENGTH_SHORT
                            ).show()
//                            findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToAuthorizedFragment())

                        }
                        is Resource.Error -> {
                            Toast.makeText(context, it.toString(), Toast.LENGTH_SHORT)
                                .show()
                        }

                    }
                }
            }
        }
    }

    private fun register(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                viewModel.register(task)
            }
    }

    private fun submitForm(): Boolean = with(binding) {
        val email = emailInput.text?.toString()
        val password = passInput.text?.toString()
        val rePassword = repeatPassInput.text?.toString()

        var isFormValid = true

        if (!isValidEmail(email)) {
            emailLayout.error = ("Incorrect Email")
            isFormValid = false
        }

        if (!(password != null && password.isNotBlank() && password.length > 6)) {
            passLayout.error = ("Incorrect Password")
            isFormValid = false
        }
        if (!(rePassword != null && rePassword.isNotBlank() && rePassword == password)) {
            repeatPassLayout.error = ("Incorrect Repeat Password")
            isFormValid = false
        }

        return isFormValid
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
        passInput.doAfterTextChanged {
            passLayout.error = null
        }
        repeatPassInput.doAfterTextChanged {
            passLayout.error = null
        }
    }

}