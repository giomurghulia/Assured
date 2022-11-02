package com.insurance.assured.ui.policy

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

    override fun init() {
        binding.mainRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.mainRecycler.adapter = adapter
    }

    override fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    adapter.submitList(it)
                }
            }
        }
    }

    override fun listener() {
        binding.root.setOnRefreshListener {
            viewModel.refresh()
            binding.root.isRefreshing = false
        }    }
}