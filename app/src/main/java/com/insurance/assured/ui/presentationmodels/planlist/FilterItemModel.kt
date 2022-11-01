package com.insurance.assured.ui.presentationmodels.planlist

import com.insurance.assured.common.enums.InsuranceCategory
import com.insurance.assured.ui.enums.SelectState

data class FilterItemModel (val id: Int, val category: InsuranceCategory, var selectState: SelectState = SelectState.NOT_SELECTED)