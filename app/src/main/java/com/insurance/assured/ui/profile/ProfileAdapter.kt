package com.insurance.assured.ui.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.insurance.assured.common.MainDiffUtil
import com.insurance.assured.databinding.LayoutLogOutBinding
import com.insurance.assured.databinding.LayoutProfileItemBinding
import com.insurance.assured.databinding.LayoutRequireAuthBinding
import com.insurance.assured.databinding.LayoutSpaceItemBinding
import com.insurance.assured.databinding.LayoutTitlteItemBinding
import com.insurance.assured.databinding.LayoutUnfinishedItemBinding

class ProfileAdapter : ListAdapter<ProfileListItem, RecyclerView.ViewHolder>(MainDiffUtil()) {

    private var callBack: CallBack? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            ProfileListItem.ViewType.NO_USER.ordinal -> {
                NoUserViewHolder(
                    LayoutRequireAuthBinding.inflate(layoutInflater, parent, false)
                )
            }
            ProfileListItem.ViewType.CHANGE_EMAIL.ordinal -> {
                ChangeEmailViewHolder(
                    LayoutProfileItemBinding.inflate(layoutInflater, parent, false)
                )
            }
            ProfileListItem.ViewType.CHANGE_PASS.ordinal -> {
                ChangePassViewHolder(
                    LayoutProfileItemBinding.inflate(layoutInflater, parent, false)
                )
            }
            ProfileListItem.ViewType.SPACE.ordinal -> {
                SpaceViewHolder(
                    LayoutSpaceItemBinding.inflate(layoutInflater, parent, false)
                )
            }
            ProfileListItem.ViewType.ADD_CARD.ordinal -> {
                AddCardViewHolder(
                    LayoutProfileItemBinding.inflate(layoutInflater, parent, false)
                )
            }
            ProfileListItem.ViewType.CARD.ordinal -> {
                CardViewHolder(
                    LayoutUnfinishedItemBinding.inflate(layoutInflater, parent, false)
                )
            }
            ProfileListItem.ViewType.LOG_OUT.ordinal -> {
                LogOutViewHolder(
                    LayoutLogOutBinding.inflate(layoutInflater, parent, false)
                )
            }
            ProfileListItem.ViewType.TITLE.ordinal -> {
                TitleViewHolder(
                    LayoutTitlteItemBinding.inflate(layoutInflater, parent, false)
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
            is NoUserViewHolder -> holder.bind(item as ProfileListItem.NoUserItem)
            is ChangeEmailViewHolder -> holder.bind(item as ProfileListItem.ChangeEmailItem)
            is ChangePassViewHolder -> holder.bind(item as ProfileListItem.ChangePassItem)
            is AddCardViewHolder -> holder.bind(item as ProfileListItem.AddCardItem)
            is SpaceViewHolder -> holder.bind(item as ProfileListItem.SpaceItem)
            is LogOutViewHolder -> holder.bind(item as ProfileListItem.LogOutItem)
            is TitleViewHolder -> holder.bind(item as ProfileListItem.TitleItem)
        }
    }

    inner class NoUserViewHolder(private val binding: LayoutRequireAuthBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ProfileListItem.NoUserItem) {

        }
    }

    inner class ChangeEmailViewHolder(private val binding: LayoutProfileItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ProfileListItem.ChangeEmailItem) {

        }
    }

    inner class ChangePassViewHolder(private val binding: LayoutProfileItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ProfileListItem.ChangePassItem) {

        }
    }

    inner class AddCardViewHolder(private val binding: LayoutProfileItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ProfileListItem.AddCardItem) {

        }
    }

    inner class CardViewHolder(private val binding: LayoutUnfinishedItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ProfileListItem.CardItem) {

        }
    }

    inner class TitleViewHolder(private val binding: LayoutTitlteItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ProfileListItem.TitleItem) {
            binding.titleText.text = item.title
            binding.subTitleText.text = item.subTitle
        }
    }

    inner class SpaceViewHolder(private val binding: LayoutSpaceItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ProfileListItem.SpaceItem) {}
    }

    inner class LogOutViewHolder(private val binding: LayoutLogOutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ProfileListItem.LogOutItem) {}
    }

    interface CallBack {
    }
}