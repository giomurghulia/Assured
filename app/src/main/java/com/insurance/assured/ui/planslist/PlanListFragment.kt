package com.insurance.assured.ui.planslist


import android.view.View
import android.widget.*
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.*
import com.google.android.material.internal.NavigationMenuView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.insurance.assured.R
import com.insurance.assured.common.enums.InsuranceCategory
import com.insurance.assured.databinding.FragmentPlanListBinding
import com.insurance.assured.ui.basefragments.BaseFragment
import com.insurance.assured.ui.planslist.adapters.FiltersAdapter
import com.insurance.assured.ui.planslist.adapters.PlansAdapter
import com.insurance.assured.ui.presentationmodels.planlist.PlanListItemModel
import com.insurance.assured.ui.sharedviewmodel.CheckoutViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

@AndroidEntryPoint
class PlanListFragment : BaseFragment<FragmentPlanListBinding>(FragmentPlanListBinding::inflate) {
    private val viewModel: PlanListViewModel by viewModels()
    private var type = "All"
    private val sharedViewModel: CheckoutViewModel by activityViewModels()
    private val plansListAdapter: PlansAdapter by lazy {
        PlansAdapter({ viewModel.getData(true) }) {
            sharedViewModel.onItemChoose(it)
            if (Firebase.auth.currentUser != null)
                findNavController().navigate(PlanListFragmentDirections.actionGlobalCheckoutTwoFragment())
            else
                findNavController().navigate(PlanListFragmentDirections.actionGlobalNotSignedInFragment())
        }
    }

    private var filterBinding: FilterViewBinder? = null
    private val filtersAdapter: FiltersAdapter by lazy {
        FiltersAdapter {
            viewModel.onFilterSelected(model = it)
            if (it.category == InsuranceCategory.HOT_OFFERS)
                binding.filter.visibility = GONE
            else
                binding.filter.visibility = VISIBLE
        }
    }

    override fun init() {
        val formatter = SimpleDateFormat.getDateInstance()
        //toast(formatter.format(System.currentTimeMillis()).toString())
        bindFilter()
        checkNavArgs()
        bindRecyclers()
        viewModel.getData()
    }

    override fun observe() {
        filterSelectStateListener()
        listStateObserver()
        filterViewStateObserver()
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                sharedViewModel.checkOutState.collect {
                    if (it.insurancePacket != null && sharedViewModel.signedAndBack) {
                        sharedViewModel.signedAndBack = false
                        if (Firebase.auth.currentUser != null)
                            findNavController().navigate(PlanListFragmentDirections.actionGlobalCheckoutTwoFragment())
                        else
                            findNavController().navigate(PlanListFragmentDirections.actionGlobalNotSignedInFragment())
                    }
                }
            }
        }
    }


    override fun listener() {
        onRefreshListener()
        filterListener()
        questionListener()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        filterBinding = null
    }

    private fun checkNavArgs() {
        val args: PlanListFragmentArgs by navArgs()
        var position: Int = 0
        if (args.type != null && args.type!!.toString().trim() != "nulll") {
            if (args.type!! != InsuranceCategory.HOT_OFFERS.toString())
                binding.filter.visibility = VISIBLE
            requireArguments().clear()
            viewModel.filterSelectState.value.forEachIndexed { index, model ->
                if (model.category.toString() == args.type!!) {
                    position = index
                    return@forEachIndexed
                }
            }
        }
        viewModel.onFilterSelected(viewModel.filterSelectState.value[position])
    }

    private fun bindFilter() {
        filterBinding = FilterViewBinder(binding.filterView.getHeaderView(0))
        filterBinding!!.spinner.adapter = ArrayAdapter(
            requireContext(),
            R.layout.soinner_item,
            listOf("All", "Car", "Truck", "Moto")
        )
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

    }

    private fun bindRecyclers() {
        binding.categories.adapter = filtersAdapter
        binding.categories.layoutManager = LinearLayoutManager(context, HORIZONTAL, false)

        binding.plansList.adapter = plansListAdapter

        binding.plansList.layoutManager = LinearLayoutManager(context, VERTICAL, false)
    }

    private fun onRefreshListener() {
        binding.root.setOnRefreshListener {
            viewModel.getData(true)
            binding.drawerLayout.closeDrawer(binding.filterView)
            filterBinding!!.reset()
            binding.root.isRefreshing = false
        }
    }

    private fun filterListener() {
        binding.filter.setOnClickListener {
            binding.drawerLayout.openDrawer(binding.filterView)
        }
        filterBinding!!.spinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
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
        filterBinding!!.filterButton.setOnClickListener {
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
        filterBinding!!.resetButton.setOnClickListener {
            filterBinding!!.reset()
        }
    }

    private fun filterSelectStateListener() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.filterSelectState.collect {
                    filtersAdapter.submitList(it)
                    type = "All"
                }
            }
        }
    }

    private fun listStateObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.plansListState.collect {
                    plansListAdapter.submitList(
                        it.data!!.toMutableList().apply { add(PlanListItemModel(-3)) })
                    it.error?.let {
                        toast("please check connection and try again")
                    }
                    binding.filter.isClickable =
                        it.data!!.isEmpty() || (it.data[0].id != -2 && it.data[0].id != -1)
                    delay(300)
                    binding.plansList.smoothScrollToPosition(0)
                }
            }
        }
    }

    private fun filterViewStateObserver() {
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

    private fun questionListener() {
        binding.questionImage.setOnClickListener {
            findNavController().navigate(PlanListFragmentDirections.actionGlobalQuestionFragment())
        }
    }

}