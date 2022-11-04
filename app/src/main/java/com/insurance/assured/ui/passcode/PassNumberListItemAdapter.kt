package com.insurance.assured.ui.passcode

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.insurance.assured.R
import com.insurance.assured.common.MainDiffUtil
import com.insurance.assured.databinding.LayoutPassNumItemBinding


class PassNumberListItemAdapter :
    ListAdapter<PassNumberListItem, RecyclerView.ViewHolder>(MainDiffUtil()) {
    private var callBack: CallBack? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            PassNumberListItem.ViewType.NUMBER.ordinal -> {
                NumberViewHolder(
                    LayoutPassNumItemBinding.inflate(layoutInflater, parent, false)
                )
            }
            PassNumberListItem.ViewType.FINGER.ordinal -> {
                FingerViewHolder(
                    LayoutPassNumItemBinding.inflate(layoutInflater, parent, false)
                )
            }
            PassNumberListItem.ViewType.DELETE.ordinal -> {
                DeleteViewHolder(
                    LayoutPassNumItemBinding.inflate(layoutInflater, parent, false)
                )
            }
            else -> throw IllegalStateException()

        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).viewType.ordinal
    }

    fun setCallBack(callBack: CallBack) {
        this.callBack = callBack
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)

        when (holder) {
            is NumberViewHolder -> {
                holder.bind(item as PassNumberListItem.NumberItem)
            }
            is FingerViewHolder -> {
                holder.bind()
            }
            is DeleteViewHolder -> {
                holder.bind()
            }
        }
    }

    inner class NumberViewHolder(
        private val binding: LayoutPassNumItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PassNumberListItem.NumberItem) {
            binding.numberText.text = item.id.toString()

            binding.numberText.setOnClickListener {
                callBack?.onItemClick(item.id)
            }
        }
    }

    inner class FingerViewHolder(
        private val binding: LayoutPassNumItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            binding.iconImage.setImageResource(R.drawable.ic_icon_finger)

            binding.iconImage.setOnClickListener {
                callBack?.onItemClick(10)
            }
        }
    }

    inner class DeleteViewHolder(
        private val binding: LayoutPassNumItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            binding.iconImage.setImageResource(R.drawable.ic_icon_delete)

            binding.iconImage.setOnClickListener {
                callBack?.onItemClick(11)
            }
        }
    }

    interface CallBack {
        fun onItemClick(itemId: Int)
    }
}