package com.insurance.assured.ui.pages.profile

import android.os.Handler
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.insurance.assured.R
import com.insurance.assured.databinding.FragmentProfileBinding
import com.insurance.assured.ui.basefragments.BaseFragment
import com.insurance.assured.ui.pages.home.HomeFragmentDirections
import com.insurance.assured.ui.pages.policy.PolicyFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(
    FragmentProfileBinding::inflate
) {
    private val viewModel: ProfileViewModel by viewModels()
    private val adapter = ProfileAdapter()

    private val currentUser get() = Firebase.auth.currentUser

    private val handler = Handler()
    private val recyclerScrollRunnable = Runnable {
        binding.mainRecycler.smoothScrollToPosition(0)
    }

    override fun init() {
        viewModel.refresh()
        if(Firebase.auth.currentUser == null){
            binding.backImage.visibility = RecyclerView.GONE
        }

        binding.mainRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.mainRecycler.adapter = adapter

        adapter.setCallBack(object : ProfileAdapter.CallBack {
            override fun onItemClick(item: ProfileListItem.ViewType) {
                when (item) {
                    ProfileListItem.ViewType.NO_USER -> {
                        findNavController().navigate(ProfileFragmentDirections.actionGlobalSignInFragment())
                    }
                    ProfileListItem.ViewType.LOG_OUT -> {
                        viewModel.longOut()
                        findNavController().navigate(R.id.homeFragment)
                    }
                    ProfileListItem.ViewType.ADD_CARD -> {
                        findNavController().navigate(ProfileFragmentDirections.actionGlobalAddCardFragment())
                    }
                    ProfileListItem.ViewType.CHANGE_PASS -> {
                        findNavController().navigate(ProfileFragmentDirections.actionGlobalChangePasswordFragment())
                    }
                    ProfileListItem.ViewType.CHANGE_EMAIL -> {
                        findNavController().navigate(ProfileFragmentDirections.actionGlobalChangeEmailFragment())
                    }
                    else -> {}
                }
            }

            override fun onCardDeleteClick(cardToken: String) {
                viewModel.deleteCard(currentUser?.email!!, cardToken)
            }
        })
    }

    override fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    adapter.submitList(it)
                    scrollToTop()
                }
            }
        }
    }

    override fun listener() {
        binding.root.setOnRefreshListener {
            viewModel.refresh()
            binding.root.isRefreshing = false
        }

        binding.questionImage.setOnClickListener {
            findNavController().navigate(PolicyFragmentDirections.actionGlobalQuestionFragment())
        }
        binding.backImage.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionGlobalChatFragment())
        }
    }

    private fun scrollToTop() {
        handler.postDelayed(recyclerScrollRunnable, 300)
    }

    override fun onDestroyView() {
        handler.removeCallbacks(recyclerScrollRunnable)
        super.onDestroyView()
    }

}