package com.insurance.assured.ui.home

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.insurance.assured.R
import com.insurance.assured.databinding.FragmentHomeBinding
import com.insurance.assured.ui.basefragments.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.lang.Math.abs

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
                    HomeListItem.ViewType.CATEGORIES -> {}
                    HomeListItem.ViewType.CAR_BANNER -> {}
                    HomeListItem.ViewType.HEALTH_BANNER -> {}
                    HomeListItem.ViewType.SHIMMER_BANNER -> {}
                    HomeListItem.ViewType.ERROR_MAIN_BANNER -> {
                        viewModel.refreshMainBanners()
                    }
                    HomeListItem.ViewType.ERROR_CAR_BANNER -> {
                        viewModel.refreshCardBanners()
                    }
                }
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