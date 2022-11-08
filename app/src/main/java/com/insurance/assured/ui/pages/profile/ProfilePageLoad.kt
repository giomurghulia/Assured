package com.insurance.assured.ui.pages.profile

import com.insurance.assured.common.resource.Result
import com.insurance.assured.domain.models.card.CardModel

data class ProfilePageLoad(
    val profile: List<ProfileListItem>? = null,
    val card: Result<List<CardModel>>? = null
)