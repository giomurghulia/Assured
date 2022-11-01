package com.insurance.assured.ui.policy

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.insurance.assured.common.extensions.load
import com.insurance.assured.common.resource.Result
import com.insurance.assured.databinding.FragmentPolicyBinding
import com.insurance.assured.ui.basefragments.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PolicyFragment : BaseFragment<FragmentPolicyBinding>(
    FragmentPolicyBinding::inflate
) {
    private val viewModel: PolicyViewModel by viewModels()

    override fun init() {
        viewModel.getPolicy()
    }

    override fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    when (it) {
                        is Result.Success -> {
                            binding.bannerImage.load(it.data.firstOrNull()?.health_insurance!![0].banner)
                            binding.titleText.text = it.data.firstOrNull()?.pet_insurance!![0].title
                        }
                        is Result.Loading -> {
                        }
                        is Result.Error -> {
                        }

                    }

                }
            }
        }
    }

    override fun listener() {
        super.listener()
    }
}