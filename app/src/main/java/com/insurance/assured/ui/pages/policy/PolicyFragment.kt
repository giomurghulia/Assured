package com.insurance.assured.ui.pages.policy

import android.os.Handler
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.insurance.assured.common.enums.AuthEnum
import com.insurance.assured.databinding.FragmentPolicyBinding
import com.insurance.assured.ui.basefragments.BaseFragment
import com.insurance.assured.ui.pages.home.HomeFragmentDirections
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
        if(Firebase.auth.currentUser == null){
            binding.backImage.visibility = RecyclerView.GONE
        }
        viewModel.getUserData()

        binding.mainRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.mainRecycler.adapter = adapter

        adapter.setCallBack(object : PolicyAdapter.CallBack {
            override fun onAuthClick(item: AuthEnum) {
                when (item) {
                    AuthEnum.SIGNE_IN -> {
                        findNavController().navigate(PolicyFragmentDirections.actionGlobalSignInFragment())
                    }
                    AuthEnum.SIGNE_UP -> {
                        findNavController().navigate(PolicyFragmentDirections.actionGlobalSignInFragment())
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

            override fun onItemClick(item: PolicyListItem.ViewType) {
                when(item){
                    PolicyListItem.ViewType.ERROR_USER_DATA ->{
                        viewModel.refreshUserData()
                    }
                    PolicyListItem.ViewType.ERROR_POLICY ->{
                        viewModel.refreshUserPolicies()
                    }
                    PolicyListItem.ViewType.CASHLESS_BANNER ->{
                        findNavController().navigate(PolicyFragmentDirections.actionGlobalCashlessFragment())
                    }
                    PolicyListItem.ViewType.NO_POLICY ->{
//                        findNavController().navigate(R.id.planListFragment)
                    }
                    else -> {}
                }
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