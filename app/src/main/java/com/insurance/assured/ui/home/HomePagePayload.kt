package com.insurance.assured.ui.home

import com.insurance.assured.domain.models.banner.BannersModel
import com.insurance.assured.common.resource.Result
import com.insurance.assured.ui.presentationmodels.planlist.PlanListItemModel


data class HomePagePayload(
    val mainBanners: Result<List<BannersModel>> = Result.Loading,
    val hotBanners: Result<List<PlanListItemModel>> = Result.Loading,
    )
