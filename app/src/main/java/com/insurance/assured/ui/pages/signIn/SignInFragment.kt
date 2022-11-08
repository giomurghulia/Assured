package com.insurance.assured.ui.pages.signIn

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.insurance.assured.databinding.FragmentSignInBinding
import kotlinx.coroutines.launch

class SignInFragment : Fragment() {
    private lateinit var binding: FragmentSignInBinding

    private val viewModel: SignInViewModel by viewModels()

    private val auth = Firebase.auth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateErrorStates()

        binding.signInButton.setOnClickListener {
            if (submitForm()) {
                val email = binding.emailInput.text.toString()
                val password = binding.passInput.text.toString()

                logIn(email, password)
            }
        }
        binding.signUpButton.setOnClickListener {
            if (submitForm()) {
                val email = binding.emailInput.text.toString()
                val password = binding.passInput.text.toString()

                register(email, password)
            }
        }

        binding.backImage.setOnClickListener {
            val result = true
            // Use the Kotlin extension in the fragment-ktx artifact
            setFragmentResult("userAuthResult", bundleOf("result" to result))

            requireActivity().onBackPressed()
        }


        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    when (it) {
                        is Resource.Success -> {
                            Toast.makeText(context, "signInWithEmail:success", Toast.LENGTH_SHORT)
                                .show()

                            val result = true
                            // Use the Kotlin extension in the fragment-ktx artifact
                            setFragmentResult("userAuthResult", bundleOf("result" to result))

                            requireActivity().onBackPressed()

                        }
                        is Resource.Error -> {
                            Toast.makeText(context, it.errorMessage, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun logIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                viewModel.login(task)
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

        var isFormValid = true

        if (!isValidEmail(email)) {
            emailLayout.error = ("Incorrect Email")
            isFormValid = false
        }

        if (!(password != null && password.isNotBlank() && password.length > 6)) {
            passLayout.error = ("Incorrect Password")
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
    }
}