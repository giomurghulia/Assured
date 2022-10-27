package com.insurance.assured.ui.enums

import androidx.annotation.ColorRes
import com.insurance.assured.R


enum class SelectState {
    SELECTED {
        override val color: Int
            get() = R.color.selected_option
    },
    NOT_SELECTED{
        override val color: Int
            get() = R.color.not_selected_option
    },
    DEFAULT;
    @ColorRes
    open val color: Int = R.color.not_selected_option
}