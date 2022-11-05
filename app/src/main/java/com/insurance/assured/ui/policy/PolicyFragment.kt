package com.insurance.assured.ui.policy

import android.os.Handler
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.insurance.assured.databinding.FragmentPolicyBinding
import com.insurance.assured.ui.basefragments.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PolicyFragment : BaseFragment<FragmentPolicyBinding>(
    FragmentPolicyBinding::inflate
) {
    private val viewModel: PolicyViewModel by viewModels()
    private val adapter = PolicyAdapter()

    private val handler = Handler()
    private val recyclerScrollRunnable = Runnable {
        binding.mainRecycler.smoothScrollToPosition(0)
    }

    override fun init() {
        binding.mainRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.mainRecycler.adapter = adapter
    }

    override fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    scrollToTop()
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
    }

    private fun scrollToTop() {
        handler.postDelayed(recyclerScrollRunnable, 0)
    }

    override fun onDestroyView() {
        handler.removeCallbacks(recyclerScrollRunnable)
        super.onDestroyView()
    }

}