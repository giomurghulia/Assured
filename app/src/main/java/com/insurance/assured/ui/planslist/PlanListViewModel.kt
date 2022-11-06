package com.insurance.assured.ui.planslist

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.insurance.assured.common.enums.HouseInsuranceType
import com.insurance.assured.common.enums.HouseInsuranceType.Companion.toHouseInsuranceType
import com.insurance.assured.common.enums.InsuranceCategory
import com.insurance.assured.common.enums.VehicleInsuranceType
import com.insurance.assured.common.enums.VehicleInsuranceType.Companion.toVehicleInsuranceType
import com.insurance.assured.common.resource.Resource
import com.insurance.assured.domain.models.plans.*
import com.insurance.assured.domain.usecases.plansusecases.filterdata.FilterDataUseCase
import com.insurance.assured.domain.usecases.plansusecases.getdata.GetDataUseCase
import com.insurance.assured.ui.enums.SelectState
import com.insurance.assured.ui.mappers.*
import com.insurance.assured.ui.presentationmodels.planlist.FilterItemModel
import com.insurance.assured.ui.presentationmodels.planlist.PlanListItemModel
import com.insurance.assured.ui.viewstate.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class PlanListViewModel @Inject constructor(
    private val listsProvider: InitialListsProvider,
    private val useCase: GetDataUseCase,
    private val filterDataUseCase: FilterDataUseCase
) :
    ViewModel() {
    private val _filterViewState: MutableStateFlow<List<String>> by lazy {
        MutableStateFlow(listsProvider.getLifeItems())
    }
    val filterViewState = _filterViewState.asStateFlow()
    private val _filterSelectState: MutableStateFlow<List<FilterItemModel>> by lazy {
        MutableStateFlow(listsProvider.getFilterList())
    }
    private var isFiltering = false
    val filterSelectState = _filterSelectState.asStateFlow()

    private val _plansListState: MutableStateFlow<ViewState<List<PlanListItemModel>>> by lazy {
        MutableStateFlow(ViewState(data = listsProvider.getShimmerList(), load = true))
    }
    private var categorySelected: InsuranceCategory = InsuranceCategory.HOT_OFFERS
    val plansListState = _plansListState.asStateFlow()
    private var currentUnfilteredList: List<PlanListItemModel> = listOf()


    fun onFilterSelected(model: FilterItemModel) {
        if (isFiltering) return
        viewModelScope.launch(Dispatchers.Default) {
            val listNow = _filterSelectState.value
            categorySelected = model.category
            val position = listNow.indexOf(model)
            if (position != -1) {
                if (listNow[position].selectState != SelectState.SELECTED) {
                    val newList = listNow.map { it.copy(selectState = SelectState.NOT_SELECTED) }
                    newList[position].selectState = SelectState.SELECTED
                    _filterSelectState.value = newList
                }
                when (categorySelected) {
                    InsuranceCategory.VEHICLE -> _filterViewState.value =
                        listsProvider.getVehicleItems()
                    InsuranceCategory.PET -> _filterViewState.value = listsProvider.getPetItems()
                    InsuranceCategory.HEALTH -> _filterViewState.value =
                        listsProvider.getLifeItems()
                    InsuranceCategory.HOUSE -> _filterViewState.value =
                        listsProvider.getHouseItems()
                    else -> {}
                }
                getData(category = categorySelected)
            }
        }
    }

    fun getData(forceReset: Boolean = false, category: InsuranceCategory = categorySelected) {
        viewModelScope.launch(Dispatchers.IO) {
            _plansListState.value = _plansListState.value.copy(
                load = true,
                data = listsProvider.getShimmerList(),
                error = null
            )
            when (category) {
                InsuranceCategory.HOT_OFFERS -> {
                    getDataGlobal(
                        { useCase.getHotPlans() },
                        {
                            try {
                                (it as List<PlansModel<*>>).map { it.toPresenterModel() }
                            } catch (e: Exception) {
                                listsProvider.getShimmerList()
                            }
                        }, category
                    )
                }
                InsuranceCategory.HEALTH -> {
                    getDataGlobal(
                        { useCase.getLifePlans(forceReset) },
                        {
                            try {
                                (it as List<PlansModel<LifeSpec>>).map { it.lifeToPresenterModel() }
                            } catch (e: Exception) {
                                listsProvider.getShimmerList()
                            }
                        }, category
                    )

                }
                InsuranceCategory.VEHICLE -> {
                    getDataGlobal(
                        { useCase.getVehiclePlans(forceReset) },
                        {
                            try {
                                (it as List<PlansModel<VehicleSpecs>>).map { it.vehicleToPresenterModel() }
                            } catch (e: Exception) {
                                listsProvider.getShimmerList()
                            }
                        }, category
                    )
                }
                InsuranceCategory.PET -> {
                    getDataGlobal(
                        { useCase.getPetPlans(forceReset) },
                        {
                            try {
                                (it as List<PlansModel<PetSpec>>).map { it.petToPresenterModel() }
                            } catch (e: Exception) {
                                listsProvider.getShimmerList()
                            }
                        }, category
                    )
                }
                InsuranceCategory.HOUSE -> {
                    getDataGlobal(
                        { useCase.getHousePlans(forceReset) },
                        {
                            try {
                                (it as List<PlansModel<HouseSpecs>>).map { it.houseToPresenterModel() }
                            } catch (e: Exception) {
                                listsProvider.getShimmerList()
                            }
                        }, category
                    )
                }
                else -> {}
            }

        }
    }


    private suspend fun getDataGlobal(
        useCaseFun: suspend () -> Any,
        mapper: (Any) -> List<PlanListItemModel>,
        category: InsuranceCategory
    ) {
        when (val resource = useCaseFun.invoke()) {
            is Resource.Success<*> -> {
                if (category == categorySelected)
                    _plansListState.value =
                        _plansListState.value.copy(
                            data = mapper(resource.model!!),
                            load = false,
                            error = null
                        )
            }
            is Resource.Error<*> -> {
                if (resource.placeholder == null || mapper(resource.placeholder).isEmpty()) {
                    if (category == categorySelected)
                        _plansListState.value = _plansListState.value.copy(
                            data = listOf(
                                PlanListItemModel(-2)
                            ), load = false, error = resource.message
                        )
                } else {
                    if (category == categorySelected)
                        _plansListState.value = _plansListState.value.copy(
                            data = mapper(resource.placeholder),
                            load = false,
                            error = resource.message
                        )
                }
            }
        }
        currentUnfilteredList = _plansListState.value.data ?: listOf()
    }

    fun onFilter(
        fromPrice: String?,
        toPrice: String?,
        fromMaxMoney: String?,
        toMaxMoney: String?,
        type: String,
        fromPeriod: String?,
        toPeriod: String?
    ) {
        viewModelScope.launch(Dispatchers.Default) {
            val fromPriceF = if (!fromPrice.isNullOrEmpty()) fromPrice.toFloat() else 0.0f
            val toPriceF = if (!toPrice.isNullOrEmpty()) toPrice.toFloat() else Float.MAX_VALUE
            val fromMaxMoneyF = if (!fromMaxMoney.isNullOrEmpty()) fromMaxMoney.toFloat() else 0.0f
            val toMaxMoneyF =
                if (!toMaxMoney.isNullOrEmpty()) toMaxMoney.toFloat() else Float.MAX_VALUE
            val fromPeriodI = if (!fromPeriod.isNullOrEmpty()) fromPeriod.toInt() else 0
            val toPeriodI = if (!toPeriod.isNullOrEmpty()) toPeriod.toInt() else Int.MAX_VALUE

            isFiltering = true
            try {
                when (categorySelected) {
                    InsuranceCategory.HOUSE -> {
                        val typeT: HouseInsuranceType? =
                            if (type == "All") null else type.lowercase(
                                Locale.ROOT
                            ).toHouseInsuranceType()
                        _plansListState.value = _plansListState.value.copy(
                            data = filterDataUseCase.filterHousePlansUseCase(
                                currentUnfilteredList.map { it.toHouseDomainModel() },
                                typeT,
                                fromMaxMoneyF,
                                toMaxMoneyF,
                                fromPriceF,
                                toPriceF,
                                fromPeriodI,
                                toPeriodI
                            ).map { it.toPresenterModel() },
                            error = null,
                            load = false
                        )
                    }
                    InsuranceCategory.HEALTH -> {
                        var personCountFrom: Int = 0
                        var personCountTo: Int = Int.MAX_VALUE

                        if (type != "All" && type != "5+") {
                            personCountFrom = type.toInt()
                            personCountTo = type.toInt()
                        } else if (type == "5+")
                            personCountFrom = 5
                        _plansListState.value = _plansListState.value.copy(
                            data = filterDataUseCase.filterLifePlansUseCase(
                                currentUnfilteredList.map { it.toLifeDomainModel() },
                                personCountFrom,
                                personCountTo,
                                fromMaxMoneyF,
                                toMaxMoneyF,
                                fromPriceF,
                                toPriceF,
                                fromPeriodI,
                                toPeriodI
                            ).map { it.toPresenterModel() },
                            error = null,
                            load = false
                        )
                    }
                    InsuranceCategory.PET -> {
                        val typeT: String? =
                            if (type == "All") null else type.lowercase()
                        _plansListState.value = _plansListState.value.copy(
                            data = filterDataUseCase.filterPetPlansUseCase(
                                currentUnfilteredList.map { it.toPetDomainModel() },
                                typeT,
                                fromMaxMoneyF,
                                toMaxMoneyF,
                                fromPriceF,
                                toPriceF,
                                fromPeriodI,
                                toPeriodI
                            ).map { it.toPresenterModel() },
                            error = null,
                            load = false
                        )
                    }
                    InsuranceCategory.VEHICLE -> {
                        val typeT: VehicleInsuranceType? =
                            if (type == "All") null else type.lowercase(
                                Locale.ROOT
                            ).toVehicleInsuranceType()
                        _plansListState.value = _plansListState.value.copy(
                            data = filterDataUseCase.filterVehicleUseCase(
                                currentUnfilteredList.map { it.toVehicleDomainModel() },
                                typeT,
                                fromMaxMoneyF,
                                toMaxMoneyF,
                                fromPriceF,
                                toPriceF,
                                fromPeriodI,
                                toPeriodI
                            ).map { it.toPresenterModel() },
                            error = null,
                            load = false
                        )
                    }
                    else -> {}
                }
            } catch (e: Exception) {
            }

            isFiltering = false
        }
    }

}