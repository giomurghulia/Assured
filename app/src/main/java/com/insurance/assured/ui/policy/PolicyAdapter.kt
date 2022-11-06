package com.insurance.assured.ui.policy

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.insurance.assured.common.MainDiffUtil
import com.insurance.assured.common.enums.AuthEnum
import com.insurance.assured.common.extensions.load
import com.insurance.assured.databinding.*


class PolicyAdapter :
    ListAdapter<PolicyListItem, RecyclerView.ViewHolder>(MainDiffUtil()) {

    private var callBack: CallBack? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            PolicyListItem.ViewType.NO_USER.ordinal -> {
                NoUserViewHolder(
                    LayoutRequireAuthBinding.inflate(layoutInflater, parent, false)
                )
            }
            PolicyListItem.ViewType.NO_POLICY.ordinal -> {
                NoPolicyViewHolder(
                    LayoutNoPolicyBinding.inflate(layoutInflater, parent, false)
                )
            }
            PolicyListItem.ViewType.CASHLESS_BANNER.ordinal -> {
                CashlessViewHolder(
                    LayoutCashlessBannerBinding.inflate(layoutInflater, parent, false)
                )
            }
            PolicyListItem.ViewType.USER_DATA.ordinal -> {
                AccountDataViewHolder(
                    LayoutUserDataBinding.inflate(layoutInflater, parent, false)
                )
            }
            PolicyListItem.ViewType.POLICY.ordinal -> {
                PolicyViewHolder(
                    LayoutPolicyItemBinding.inflate(layoutInflater, parent, false)
                )
            }
            PolicyListItem.ViewType.TITLE.ordinal -> {
                TitleViewHolder(
                    LayoutTitlteItemBinding.inflate(layoutInflater, parent, false)
                )
            }
            PolicyListItem.ViewType.SHIMMER_USER_DATA.ordinal -> {
                ShimmerUserDataViewHolder(
                    ShimmerUserDataBinding.inflate(layoutInflater, parent, false)
                )
            }
            PolicyListItem.ViewType.SHIMMER_POLICY.ordinal -> {
                ShimmerPolicyViewHolder(
                    ShimmerPolicyItemBinding.inflate(layoutInflater, parent, false)
                )
            }
            PolicyListItem.ViewType.ERROR_USER_DATA.ordinal -> {
                ErrorUserDataViewHolder(
                    ErrorBannerBinding.inflate(layoutInflater, parent, false)
                )
            }
            PolicyListItem.ViewType.ERROR_POLICY.ordinal -> {
                ErrorPolicyViewHolder(
                    ErrorBannerBinding.inflate(layoutInflater, parent, false)
                )
            }
            PolicyListItem.ViewType.SPACE.ordinal -> {
                SpaceViewHolder(
                    LayoutSpaceItemBinding.inflate(layoutInflater, parent, false)
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
            is NoUserViewHolder -> holder.bind(item as PolicyListItem.NoUserItem)
            is NoPolicyViewHolder -> holder.bind(item as PolicyListItem.NoPolicyItem)
            is CashlessViewHolder -> holder.bind(item as PolicyListItem.CashlessBannerItem)
            is AccountDataViewHolder -> holder.bind(item as PolicyListItem.UserDataItem)
            is PolicyViewHolder -> holder.bind(item as PolicyListItem.PolicyItem)
            is TitleViewHolder -> holder.bind(item as PolicyListItem.TitleItem)
            is ShimmerUserDataViewHolder -> holder.bind(item as PolicyListItem.ShimmerUserDataItem)
            is ShimmerPolicyViewHolder -> holder.bind(item as PolicyListItem.ShimmerPolicyItem)
            is ErrorUserDataViewHolder -> holder.bind(item as PolicyListItem.ErrorUserDataItem)
            is ErrorPolicyViewHolder -> holder.bind(item as PolicyListItem.ErrorPolicyItem)
            is SpaceViewHolder -> holder.bind(item as PolicyListItem.SpaceItem)
        }
    }


    inner class NoUserViewHolder(private val binding: LayoutRequireAuthBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PolicyListItem.NoUserItem) {
            binding.signIpnButton.setOnClickListener {
                callBack?.onAuthClick(AuthEnum.SIGNE_IN)
            }
        }

    }

    inner class NoPolicyViewHolder(private val binding: LayoutNoPolicyBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PolicyListItem.NoPolicyItem) {
            binding.findText.setOnClickListener {
                callBack?.onItemClick(PolicyListItem.ViewType.NO_POLICY)
            }
        }
    }

    inner class CashlessViewHolder(private val binding: LayoutCashlessBannerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PolicyListItem.CashlessBannerItem) {
            binding.root.setOnClickListener {
                callBack?.onItemClick(PolicyListItem.ViewType.CASHLESS_BANNER)
            }
        }
    }

    inner class AccountDataViewHolder(private val binding: LayoutUserDataBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PolicyListItem.UserDataItem) {

            binding.fullAmountText.text = item.fullAmount

            binding.policyNumText.text = item.all
            binding.houseNumText.text = item.house
            binding.healthNumText.text = item.health
            binding.carNumText.text = item.car
            binding.petNumText.text = item.pet
        }
    }

    inner class PolicyViewHolder(private val binding: LayoutPolicyItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PolicyListItem.PolicyItem) {
            binding.titleText.text = item.title
            binding.typeText.text = item.type

            binding.iconImage.load(item.banner)
            binding.root.setOnClickListener {
                callBack?.onPolicyClick(item.id)
            }

        }
    }

    inner class TitleViewHolder(private val binding: LayoutTitlteItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PolicyListItem.TitleItem) {
            binding.titleText.text = item.title
            binding.subTitleText.text = item.subTitle
        }
    }

    inner class ShimmerUserDataViewHolder(private val binding: ShimmerUserDataBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PolicyListItem.ShimmerUserDataItem) {}
    }

    inner class ShimmerPolicyViewHolder(private val binding: ShimmerPolicyItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PolicyListItem.ShimmerPolicyItem) {}
    }

    inner class ErrorUserDataViewHolder(private val binding: ErrorBannerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PolicyListItem.ErrorUserDataItem) {
            binding.root.setOnClickListener {
                callBack?.onItemClick(PolicyListItem.ViewType.ERROR_USER_DATA)
            }
        }
    }

    inner class ErrorPolicyViewHolder(private val binding: ErrorBannerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PolicyListItem.ErrorPolicyItem) {
            binding.root.setOnClickListener {
                callBack?.onItemClick(PolicyListItem.ViewType.ERROR_POLICY)
            }
        }
    }

    inner class SpaceViewHolder(private val binding: LayoutSpaceItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PolicyListItem.SpaceItem) {}
    }


    interface CallBack {
        fun onAuthClick(item: AuthEnum)
        fun onPolicyClick(itemId: String)
        fun onItemClick(item:PolicyListItem.ViewType)
    }
}