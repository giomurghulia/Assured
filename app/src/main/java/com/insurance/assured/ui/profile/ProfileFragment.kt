package com.insurance.assured.ui.profile

import android.os.Handler
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.insurance.assured.databinding.FragmentProfileBinding
import com.insurance.assured.ui.basefragments.BaseFragment
import com.insurance.assured.ui.policy.PolicyAdapter
import com.insurance.assured.ui.policy.PolicyFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(
    FragmentProfileBinding::inflate
) {
    private val viewModel: ProfileViewModel by viewModels()
    private val adapter = ProfileAdapter()

    private val handler = Handler()
    private val recyclerScrollRunnable = Runnable {
        binding.mainRecycler.smoothScrollToPosition(0)
    }

    override fun init() {
        viewModel.refresh()

        binding.mainRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.mainRecycler.adapter = adapter

        adapter.setCallBack(object : ProfileAdapter.CallBack {
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
    }

    private fun scrollToTop() {
        handler.postDelayed(recyclerScrollRunnable, 300)
    }

    override fun onDestroyView() {
        handler.removeCallbacks(recyclerScrollRunnable)
        super.onDestroyView()
    }

}