package com.insurance.assured.ui.planslist.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.insurance.assured.databinding.PlanItemBinding
import com.insurance.assured.databinding.ShimmerPlanItemBinding
import com.insurance.assured.ui.presentationmodels.planlist.PlanListItemModel

class PlansAdapter : ListAdapter<PlanListItemModel, PlansAdapter.PlansViewHolder>(PlansDiffUtil()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PlansViewHolder(
        ShimmerPlanItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: PlansViewHolder, position: Int) {
        holder.onBind(position)
    }

    inner class PlansViewHolder(private val binding: ShimmerPlanItemBinding) : ViewHolder(binding.root) {
        fun onBind(position: Int) {
            val model = getItem(position)
            with(binding) {
//                buyBtn.text = getItem(position).monthlyPayment.toString()
//                categoryIcon.setBackgroundResource(model.category.icon)
            }
        }
    }
}

class PlansDiffUtil : DiffUtil.ItemCallback<PlanListItemModel>() {
    override fun areItemsTheSame(oldItem: PlanListItemModel, newItem: PlanListItemModel) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: PlanListItemModel,
        newItem: PlanListItemModel
    ) = oldItem == newItem
}