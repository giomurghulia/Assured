package com.insurance.assured.ui.pages.home

import com.insurance.assured.domain.models.banner.BannersModel
import com.insurance.assured.common.resource.Result
import com.insurance.assured.domain.models.checkout.CheckoutDomainModel
import com.insurance.assured.ui.presentationmodels.planlist.PlanListItemModel
import com.insurance.assured.ui.presentationmodels.sharedmodel.CheckoutModel


data class HomePagePayload(
    val mainBanners: Result<List<BannersModel>> = Result.Loading,
    val hotBanners: List<PlanListItemModel>? = null,
    val unfinishedCheckout: List<CheckoutModel>? = null
)
