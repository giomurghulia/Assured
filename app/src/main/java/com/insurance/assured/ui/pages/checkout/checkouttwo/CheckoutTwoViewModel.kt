package com.insurance.assured.ui.pages.checkout.checkouttwo

import androidx.lifecycle.ViewModel
import com.insurance.assured.common.enums.InsuranceCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class CheckoutTwoViewModel @Inject constructor() : ViewModel() {
    fun getInitialList(type: InsuranceCategory, fieldCount: Int = 1) = flow {
        var list = mutableListOf<Item>()
        when (type) {
            InsuranceCategory.HEALTH -> {
                for (i in 0 until fieldCount) {
                    list.add(Item(i, "Person ${i + 1} id", type))
                }
            }
            else -> list.add(Item(0, "", type))
        }
        emit(list)
    }
}