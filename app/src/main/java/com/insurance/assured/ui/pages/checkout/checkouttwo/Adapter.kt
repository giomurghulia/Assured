package com.insurance.assured.ui.pages.checkout.checkouttwo

import android.text.InputType
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.insurance.assured.common.enums.InsuranceCategory
import com.insurance.assured.databinding.InputItemBinding

class Adapter(private val onTextChangedListener: (id: Int, text: String) -> Unit) :
    ListAdapter<Item, Adapter.InputViewHolder>(DiffUtil1()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = InputViewHolder(
        InputItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: InputViewHolder, position: Int) {
        holder.onBind(position)
    }

    inner class InputViewHolder(private val binding: InputItemBinding) : ViewHolder(binding.root) {
        fun onBind(position: Int) {
            val model = getItem(position)
            binding.id.doAfterTextChanged {
                onTextChangedListener.invoke(model.id, it.toString())
            }
            if (model.type == InsuranceCategory.HEALTH) {
                binding.id.inputType = InputType.TYPE_CLASS_NUMBER
                binding.id.hint = model.hint
            } else {
                binding.id.inputType = InputType.TYPE_CLASS_TEXT
            }
        }
    }

}

data class Item(val id: Int, val hint: String, val type: InsuranceCategory)

class DiffUtil1 : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Item, newItem: Item) = oldItem == newItem
}