package com.insurance.assured.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.insurance.assured.R
import com.insurance.assured.databinding.FragmentProfileBinding
import com.insurance.assured.ui.authorized.AuthorizedViewModel

@Suppress("UNREACHABLE_CODE")
class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding

    private val authorizedViewModel: AuthorizedViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.logoutButton.setOnClickListener {
            Firebase.auth.signOut()
            authorizedViewModel.checkUser()
        }
    }
}