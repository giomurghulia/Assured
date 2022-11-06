package com.insurance.assured.ui.planslist

import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import com.insurance.assured.R
import kotlin.io.path.fileVisitor

class FilterViewBinder(private val filterView: View) {

    val maxMoneyFrom: EditText = filterView.findViewById(R.id.max_money_from)
    val maxMoneyTo: EditText = filterView.findViewById(R.id.max_money_to)
    val monthlyPayFrom: EditText = filterView.findViewById(R.id.monthly_payment_from)
    val monthlyPayTo: EditText = filterView.findViewById(R.id.monthly_payment_to)
    val durationFrom: EditText = filterView.findViewById(R.id.duration_from)
    val durationTo: EditText = filterView.findViewById(R.id.duration_to)
    val spinner: Spinner = filterView.findViewById(R.id.spinner)
    val filterButton: Button = filterView.findViewById(R.id.filter_btn)
    val resetButton: Button = filterView.findViewById(R.id.reset_btn)
    fun reset(){
        maxMoneyFrom.setText("")
        maxMoneyTo.setText("")
        monthlyPayFrom.setText("")
        monthlyPayTo.setText("")
        durationFrom.setText("")
        durationTo.setText("")
        spinner.setSelection(0)
    }
}