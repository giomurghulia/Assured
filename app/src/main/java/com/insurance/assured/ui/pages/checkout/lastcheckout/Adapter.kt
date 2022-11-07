package com.insurance.assured.ui.pages.checkout.lastcheckout

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.insurance.assured.R
import com.insurance.assured.databinding.CardItemBinding
import com.insurance.assured.domain.models.card.CardModel

class Adapter(private val onClickListener: (card: AdapterCardModel) -> Unit) :
    ListAdapter<AdapterCardModel, Adapter.CardViewHolder>(CardDiffUtil()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CardViewHolder(
        CardItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.onBind(position)
    }

    inner class CardViewHolder(private val binding: CardItemBinding) : ViewHolder(binding.root) {
        fun onBind(position: Int) {
            val model = getItem(position)
            binding.lastDigits.text = "****${model.model.cardLastNum}"
            if(model.isChecked){
                binding.root.setBackgroundResource(R.drawable.shape_rectangle_dark_gradient)
            }else{
                binding.root.setBackgroundResource(R.drawable.shape_rectangle)
            }
            binding.root.setOnClickListener {
                onClickListener.invoke(model)
            }
        }
    }


}

data class AdapterCardModel(val model: CardModel, val isChecked: Boolean = false)

class CardDiffUtil() : DiffUtil.ItemCallback<AdapterCardModel>() {
    override fun areItemsTheSame(oldItem: AdapterCardModel, newItem: AdapterCardModel) =
        oldItem.model.cardToken == newItem.model.cardToken

    override fun areContentsTheSame(oldItem: AdapterCardModel, newItem: AdapterCardModel) =
        oldItem == newItem


}
