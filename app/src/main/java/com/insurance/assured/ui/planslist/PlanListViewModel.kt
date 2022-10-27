package com.insurance.assured.ui.planslist

import androidx.lifecycle.ViewModel
import com.insurance.assured.ui.enums.SelectState
import com.insurance.assured.ui.presentationmodels.planlist.FilterItemModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class PlanListViewModel @Inject constructor(private val filterListProvider: InitialFilterList) :
    ViewModel() {
    private val _filterSelectState: MutableStateFlow<List<FilterItemModel>> by lazy {
        MutableStateFlow(filterListProvider())
    }
    val filterSelectState = _filterSelectState.asStateFlow()


    fun onFilterSelected(position: Int) {
        val listNow = _filterSelectState.value
        if(listNow[position].selectState != SelectState.SELECTED){
            val newList = listNow.map { it.copy(selectState = SelectState.NOT_SELECTED) }
            newList[position].selectState = SelectState.SELECTED
            _filterSelectState.value = newList
        }
    }
}