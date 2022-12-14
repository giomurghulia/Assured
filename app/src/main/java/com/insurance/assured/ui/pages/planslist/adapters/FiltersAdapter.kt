package com.insurance.assured.ui.pages.planslist.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.insurance.assured.databinding.FilterItemBinding
import com.insurance.assured.ui.presentationmodels.planlist.FilterItemModel

class FiltersAdapter(private val onItemClicked: (model: FilterItemModel) -> Unit) :
    ListAdapter<FilterItemModel, FiltersAdapter.FilterViewHolder>(FilterItemDiffUtil()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = FilterViewHolder(
        FilterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        holder.onBind(position)
    }

    inner class FilterViewHolder(private val binding: FilterItemBinding) :
        ViewHolder(binding.root) {
        fun onBind(position: Int) {
            val model = getItem(position)
            with(binding.category) {
                text = model.category.toString()
                setBackgroundResource(model.selectState.color)
                setOnClickListener{
                    onItemClicked.invoke(model)
                }
            }
        }
    }
}

class FilterItemDiffUtil : DiffUtil.ItemCallback<FilterItemModel>() {
    override fun areItemsTheSame(oldItem: FilterItemModel, newItem: FilterItemModel) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: FilterItemModel, newItem: FilterItemModel) =
        oldItem == newItem

}