package com.insurance.assured.ui.profile

import android.annotation.SuppressLint
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.insurance.assured.R
import com.insurance.assured.common.MainDiffUtil
import com.insurance.assured.common.extensions.load
import com.insurance.assured.databinding.LayoutLogOutBinding
import com.insurance.assured.databinding.LayoutProfileDataBinding
import com.insurance.assured.databinding.LayoutProfileItemBinding
import com.insurance.assured.databinding.LayoutRequireAuthBinding
import com.insurance.assured.databinding.LayoutSpaceItemBinding
import com.insurance.assured.databinding.LayoutTitlteItemBinding
import com.insurance.assured.databinding.LayoutUnfinishedItemBinding
import com.insurance.assured.databinding.LayoutUserDataBinding

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
            ProfileListItem.ViewType.USER.ordinal -> {
                ProfileViewHolder(
                    LayoutProfileDataBinding.inflate(layoutInflater, parent, false)
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
            is ProfileViewHolder -> holder.bind(item as ProfileListItem.UserItem)
            is NoUserViewHolder -> holder.bind(item as ProfileListItem.NoUserItem)
            is ChangeEmailViewHolder -> holder.bind(item as ProfileListItem.ChangeEmailItem)
            is ChangePassViewHolder -> holder.bind(item as ProfileListItem.ChangePassItem)
            is CardViewHolder -> holder.bind(item as ProfileListItem.CardItem)
            is AddCardViewHolder -> holder.bind(item as ProfileListItem.AddCardItem)
            is SpaceViewHolder -> holder.bind(item as ProfileListItem.SpaceItem)
            is LogOutViewHolder -> holder.bind(item as ProfileListItem.LogOutItem)
            is TitleViewHolder -> holder.bind(item as ProfileListItem.TitleItem)
        }
    }

    inner class NoUserViewHolder(private val binding: LayoutRequireAuthBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ProfileListItem.NoUserItem) {
            binding.signIpnButton.setOnClickListener {
                callBack?.onItemClick(ProfileListItem.ViewType.NO_USER)
            }
        }
    }

    inner class ChangeEmailViewHolder(private val binding: LayoutProfileItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ProfileListItem.ChangeEmailItem) {
            binding.titleText.text = "Change Email"

            binding.root.setOnClickListener {
                callBack?.onItemClick(ProfileListItem.ViewType.CHANGE_EMAIL)
            }
        }
    }

    inner class ChangePassViewHolder(private val binding: LayoutProfileItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ProfileListItem.ChangePassItem) {
            binding.iconImage.setImageResource(R.drawable.ic_lock_svg)
            binding.titleText.text = "Change Password"

            binding.root.setOnClickListener {
                callBack?.onItemClick(ProfileListItem.ViewType.CHANGE_PASS)
            }
        }
    }

    inner class AddCardViewHolder(private val binding: LayoutProfileItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ProfileListItem.AddCardItem) {
            binding.iconImage.setImageResource(R.drawable.ic_card_icon)
            binding.rightImage.setImageResource(R.drawable.ic_plus_icon)
            binding.titleText.text = "Add new card"

            binding.root.setOnClickListener {
                callBack?.onItemClick(ProfileListItem.ViewType.ADD_CARD)
            }
        }
    }

    inner class CardViewHolder(private val binding: LayoutUnfinishedItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: ProfileListItem.CardItem) {
            binding.iconImage.setImageResource(R.drawable.ic_mastercard_icon)
            binding.rightImage.visibility = View.GONE

            binding.finishedText.text = item.cardType
            binding.titleText.text = "****" + item.cardLastNum

            binding.deleteImage.setOnClickListener {
                callBack?.onCardDeleteClick(item.cardToken)
            }
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

        fun bind(item: ProfileListItem.LogOutItem) {
            binding.logOutButton.setOnClickListener {
                callBack?.onItemClick(ProfileListItem.ViewType.LOG_OUT)
            }
        }
    }

    inner class ProfileViewHolder(private val binding: LayoutProfileDataBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ProfileListItem.UserItem) {
            binding.emailText.text = item.email
        }
    }

    interface CallBack {
        fun onItemClick(item: ProfileListItem.ViewType)
        fun onCardDeleteClick(cardToken: String)
    }
}