package com.insurance.assured.ui.home

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.insurance.assured.common.enums.InsuranceCategory
import com.insurance.assured.databinding.FragmentHomeBinding
import com.insurance.assured.ui.basefragments.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(
    FragmentHomeBinding::inflate
) {
    private val viewModel: HomeViewModel by viewModels()

    private val homeAdapter = HomeAdapter()

    override fun init() {

        binding.mainRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.mainRecycler.adapter = homeAdapter

        homeAdapter.setCallBack(object : HomeAdapter.CallBack {
            override fun onItemClick(item: HomeListItem.ViewType) {
                when (item) {
                    HomeListItem.ViewType.MAIN_BANNERS -> {}
                    HomeListItem.ViewType.HOT_BANNER -> {}
                    HomeListItem.ViewType.CASHLESS_BANNER -> {
                        findNavController().navigate(HomeFragmentDirections.actionGlobalCashlessFragment())
                    }
                    HomeListItem.ViewType.ERROR_MAIN_BANNER -> {
                        viewModel.refreshMainBanners()
                    }
                    HomeListItem.ViewType.ERROR_CAR_BANNER -> {
                        viewModel.refreshCardBanners()
                    }
                    else -> {}
                }
            }

            override fun onCategoryItemClick(type: InsuranceCategory) {
                findNavController().navigate(
                    HomeFragmentDirections.actionGlobalPlanListFragment(
                        type.toString()
                    )
                )
            }

        })

    }

    override fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    homeAdapter.submitList(it.toList())
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
}