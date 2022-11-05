package com.insurance.assured.ui.policyitem

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.insurance.assured.common.extensions.load
import com.insurance.assured.common.resource.Result
import com.insurance.assured.databinding.FragmentPolicyBinding
import com.insurance.assured.databinding.FragmentPolicyItemBinding
import com.insurance.assured.ui.basefragments.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class PolicyItemFragment : BaseFragment<FragmentPolicyItemBinding>(
    FragmentPolicyItemBinding::inflate
) {
    private val viewModel: PolicyItemViewModel by viewModels()
    private val args: PolicyItemFragmentArgs by navArgs<PolicyItemFragmentArgs>()

    override fun init() {
        val id = args.policyId
        viewModel.getItemData(id!!)

    }

    override fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    when (it) {
                        is Result.Success -> {
                            binding.bannerImage.load(it.data.banner)
                            binding.policyTitleText.text = it.data.title

                            binding.endPeriodText.text = getDate(it.data.finish_date,"d MMM, yyyy")
                            binding.periodStartText.text = getDate(it.data.start_date,"d MMM, yyyy")

                            binding.amountText.text = it.data.all_amount.toString()
                            binding.policyTypeText.text = it.data.type
                            binding.policyIdText.text = it.data.policy_number

                        }
                        is Result.Loading -> {}
                        is Result.Error -> {}

                    }
                }
            }
        }
    }

    override fun listener() {
        binding.backImage.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun getDate(epochSec: Int, dataFormatSr: String): String {
        val date = Date(epochSec.toLong() * 1000)
        val format = SimpleDateFormat(dataFormatSr, Locale.getDefault())

        return format.format(date)
    }
}