package com.insurance.assured.ui.pages.home

import com.insurance.assured.domain.models.banner.BannersModel
import com.insurance.assured.common.resource.Result


data class HomePagePayload(
    val mainBanners: Result<List<BannersModel>> = Result.Loading,
    val carBanners: Result<List<BannersModel>> = Result.Loading,
    )
