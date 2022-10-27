package com.insurance.assured.common.types

import android.view.LayoutInflater
import android.view.ViewGroup

typealias Inflater<T> = (inflater: LayoutInflater, view: ViewGroup?, attach: Boolean) -> T