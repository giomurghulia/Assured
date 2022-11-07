package com.insurance.assured.ui.pages.home

import android.os.Handler
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.insurance.assured.R
import com.insurance.assured.common.enums.InsuranceCategory
import com.insurance.assured.databinding.FragmentHomeBinding
import com.insurance.assured.ui.basefragments.BaseFragment
import com.insurance.assured.ui.presentationmodels.planlist.PlanListItemModel
import com.insurance.assured.ui.sharedviewmodel.CheckoutViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(
    FragmentHomeBinding::inflate
) {
    private val viewModel: HomeViewModel by viewModels()
    private val sharedViewModel: CheckoutViewModel by activityViewModels()

    private val homeAdapter = HomeAdapter()

    private val handler = Handler()
    private val recyclerScrollRunnable = Runnable {
        binding.mainRecycler.smoothScrollToPosition(0)
    }

    override fun init() {
        viewModel.refresh()
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
                    else -> {}
                }
            }

            override fun onCategoryItemClick(type: InsuranceCategory) {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToPlanListFragment(
                        type.toString()
                    )
                )
            }

            override fun onPolicyBuyClick(item: HomeListItem.HotBannerItem) {
                sharedViewModel.signedAndBack = true
                sharedViewModel.onItemChoose(
                    PlanListItemModel(
                        item.id,
                        item.title,
                        item.totalMoney,
                        item.slogan,
                        item.image,
                        item.monthlyPayment,
                        item.feats,
                        item.category,
                        item.durationMonth
                    )
                )
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToPlanListFragment())
            }

            override fun onUnfinishedItemClick(item: HomeListItem.UnfinishedCheckoutItem) {
                sharedViewModel.signedAndBack = true

                val model = item.insurancePacket ?: PlanListItemModel()
                sharedViewModel.onItemChoose(model)

                sharedViewModel.onUserInfoInserted(item.userId!!, item.idList!!)

                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToPlanListFragment())
            }

            override fun onDeleteItemClick(id: Int) {
                viewModel.deleteUnfinishedCheckout(id)
            }

        })

    }

    override fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    homeAdapter.submitList(it.toList())
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
            findNavController().navigate(HomeFragmentDirections.actionGlobalQuestionFragment())
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