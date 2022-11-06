package com.insurance.assured.ui.planslist.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.insurance.assured.common.extensions.load
import com.insurance.assured.databinding.ErrorBannerBinding
import com.insurance.assured.databinding.PlanItemBinding
import com.insurance.assured.databinding.ShimmerBannerBinding
import com.insurance.assured.databinding.ShimmerPlanBanerBinding
import com.insurance.assured.ui.presentationmodels.planlist.PlanListItemModel
import kotlin.math.roundToInt

class PlansAdapter(private val onErrorBannerClickListener: (position: Int) -> Unit) :
    ListAdapter<PlanListItemModel, ViewHolder>(PlansDiffUtil()) {

    companion object {
        const val ITEM = 0
        const val SHIMMER = 1
        const val ERROR = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        when (viewType) {
            ITEM -> PlansViewHolder(
                PlanItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            SHIMMER -> ShimmerViewHolder(
                ShimmerPlanBanerBinding
                    .inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
            )
            else -> ErrorViewHolder(
                ErrorBannerBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder is PlansViewHolder)
            holder.onBind(position)
        else if (holder is ErrorViewHolder)
            holder.onBind(position)
    }

    override fun getItemViewType(position: Int) =
        when (getItem(position).id) {
            -1 -> SHIMMER
            -2 -> ERROR
            else -> ITEM
        }


    inner class PlansViewHolder(private val binding: PlanItemBinding) : ViewHolder(binding.root) {
        fun onBind(position: Int) {
            val model = getItem(position)
            with(binding) {
                buyBtn.text = "${getItem(position).monthlyPayment.roundToInt()}$/month"
                categoryIcon.setBackgroundResource(model.category.icon)
                slogan.text = model.slogan
                icon.load(model.image)
                title.text = model.title
                maxMoney.text = model.totalMoney.toString()
                features.text = model.feats.joinToString(" | ")
            }
        }
    }

    inner class ShimmerViewHolder(binding: ShimmerPlanBanerBinding) :
        ViewHolder(binding.root)

    inner class ErrorViewHolder(private val binding: ErrorBannerBinding) :
        ViewHolder(binding.root) {
        fun onBind(position: Int) {
            binding.container.setOnClickListener {
                onErrorBannerClickListener.invoke(position)
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