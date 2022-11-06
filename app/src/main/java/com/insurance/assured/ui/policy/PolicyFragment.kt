package com.insurance.assured.ui.policy

import android.os.Handler
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.insurance.assured.common.enums.AuthEnum
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
        viewModel.getUserData()

        binding.mainRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.mainRecycler.adapter = adapter

        adapter.setCallBack(object : PolicyAdapter.CallBack {
            override fun onCategoryClick(item: PolicyListItem.ViewType) {
                TODO("Not yet implemented")
            }

            override fun onAuthClick(item: AuthEnum) {
                when (item) {
                    AuthEnum.SIGNE_IN -> {
                        findNavController().navigate(PolicyFragmentDirections.actionGlobalSignInFragment())
                    }
                    AuthEnum.SIGNE_UP -> {
                        findNavController().navigate(PolicyFragmentDirections.actionGlobalSignUpFragment())
                    }
                    AuthEnum.CANCEL -> {}
                }
            }

            override fun onPolicyClick(itemId: String) {
                findNavController().navigate(
                    PolicyFragmentDirections.actionGlobalPolicyItemFragment(
                        itemId
                    )
                )
            }
        })
    }

    override fun observe() {
        setFragmentResultListener("userAuthResult") { key, bundle ->
            val result = bundle.getBoolean("result")
            // Do something with the result

        }

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