package com.insurance.assured.ui.planslist


import android.view.View
import android.widget.*
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.*
import com.insurance.assured.R
import com.insurance.assured.common.enums.InsuranceCategory
import com.insurance.assured.databinding.FragmentPlanListBinding
import com.insurance.assured.ui.basefragments.BaseFragment
import com.insurance.assured.ui.home.HomeFragmentDirections
import com.insurance.assured.ui.planslist.adapters.FiltersAdapter
import com.insurance.assured.ui.planslist.adapters.PlansAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

@AndroidEntryPoint
class PlanListFragment : BaseFragment<FragmentPlanListBinding>(FragmentPlanListBinding::inflate) {
    private val viewModel: PlanListViewModel by viewModels()
    private var type = "All"
    private val plansListAdapter: PlansAdapter by lazy {
        PlansAdapter()
    }
    private var filterBinding: FilterViewBinder? = null
    private val filtersAdapter: FiltersAdapter by lazy {
        FiltersAdapter {
            viewModel.onFilterSelected(position = it)
            if (viewModel.filterSelectState.value[it].category == InsuranceCategory.HOT_OFFERS)
                binding.filter.visibility = GONE
            else
                binding.filter.visibility = VISIBLE

        }
    }

    override fun init() {
        val formatter = SimpleDateFormat.getDateInstance()
        toast(formatter.format(System.currentTimeMillis()).toString())
        filterBinding = FilterViewBinder(binding.filterView.getHeaderView(0))
        filterBinding!!.spinner.adapter = ArrayAdapter(
            requireContext(),
            R.layout.soinner_item,
            listOf("All", "Car", "Truck", "Moto")
        )
        viewModel.onFilterSelected(0)
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        bindRecyclers()
        viewModel.getData()
    }

    override fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.filterSelectState.collect {
                    filtersAdapter.submitList(it)
                    type = "All"
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.plansListState.collect {
                    plansListAdapter.submitList(it.data)
                    it.error?.let {
                        toast("please check connection and try again")
                    }
                    delay(300)
                    binding.plansList.smoothScrollToPosition(0)
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.filterViewState.collect {
                    filterBinding!!.spinner.adapter = ArrayAdapter(
                        requireContext(),
                        R.layout.soinner_item,
                        it
                    )

                }
            }
        }
    }

    private fun bindRecyclers() {
        binding.categories.adapter = filtersAdapter
        binding.categories.layoutManager = LinearLayoutManager(context, HORIZONTAL, false)

        binding.plansList.adapter = plansListAdapter

        binding.plansList.layoutManager = LinearLayoutManager(context, VERTICAL, false)
    }

    override fun listener() {
        binding.root.setOnRefreshListener {
            viewModel.getData(true)
            binding.root.isRefreshing = false
        }
        binding.filter.setOnClickListener {
            binding.drawerLayout.openDrawer(binding.filterView)
        }
        filterBinding!!.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                type = viewModel.filterViewState.value[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }

        filterBinding!!.filterButton.setOnClickListener{
            with(filterBinding!!) {
                viewModel.onFilter(
                    monthlyPayFrom.text.toString(),
                    monthlyPayTo.text.toString(),
                    maxMoneyFrom.text.toString(),
                    maxMoneyTo.text.toString(),
                    type,
                    durationFrom.text.toString(),
                    durationTo.text.toString()
                )
            }
            binding.drawerLayout.closeDrawer(binding.filterView)
        }

        binding.questionImage.setOnClickListener {
            findNavController().navigate(PlanListFragmentDirections.actionGlobalQuestionFragment())
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        filterBinding = null
    }
}