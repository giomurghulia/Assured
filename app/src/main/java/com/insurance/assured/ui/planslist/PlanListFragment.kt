package com.insurance.assured.ui.plansList


import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.*
import com.insurance.assured.common.enums.InsuranceCategory
import com.insurance.assured.databinding.FragmentPlanListBinding
import com.insurance.assured.ui.basefragments.BaseFragment
import com.insurance.assured.ui.planslist.PlanListViewModel
import com.insurance.assured.ui.planslist.adapters.FiltersAdapter
import com.insurance.assured.ui.planslist.adapters.PlansAdapter
import com.insurance.assured.ui.presentationmodels.planlist.PlanListItemModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PlanListFragment : BaseFragment<FragmentPlanListBinding>(FragmentPlanListBinding::inflate) {
    private val viewModel: PlanListViewModel by viewModels()
    override fun init() {

        binding.categories.adapter = FiltersAdapter {
            viewModel.onFilterSelected(position = it)
            if (viewModel.filterSelectState.value[it].category == InsuranceCategory.HOT_OFFERS)
                binding.filter.visibility = GONE
            else
                binding.filter.visibility = VISIBLE
        }
        binding.categories.layoutManager = LinearLayoutManager(context, HORIZONTAL, false)

        binding.plansList.adapter = PlansAdapter().apply {
            submitList(
                listOf(
                    PlanListItemModel(
                        7,
                        "",
                        1.0f,
                        "",
                        "",
                        1.0f,
                        listOf(),
                        InsuranceCategory.VEHICLE
                    ),
                    PlanListItemModel(1, "", 1.0f, "", "", 1.0f, listOf(), InsuranceCategory.HOUSE),
                    PlanListItemModel(6, "", 1.0f, "", "", 1.0f, listOf(), InsuranceCategory.PET),
                    PlanListItemModel(5, "", 1.0f, "", "", 1.0f, listOf(), InsuranceCategory.LIFE),
                    PlanListItemModel(4, "", 1.0f, "", "", 1.0f, listOf(), InsuranceCategory.HOUSE),
                    PlanListItemModel(
                        3,
                        "",
                        1.0f,
                        "",
                        "",
                        1.0f,
                        listOf(),
                        InsuranceCategory.VEHICLE
                    ),
                    PlanListItemModel(2, "", 1.0f, "", "", 100.0f, listOf(), InsuranceCategory.LIFE)
                )
            )
            toast(currentList.size.toString())
        }

        binding.plansList.layoutManager = LinearLayoutManager(context, VERTICAL, false)

    }

    override fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.filterSelectState.collect {
                    (binding.categories.adapter as FiltersAdapter).submitList(it)
                }
            }
        }
    }
}