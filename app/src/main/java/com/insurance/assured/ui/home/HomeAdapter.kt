package com.insurance.assured.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.insurance.assured.R
import com.insurance.assured.common.MainDiffUtil
import com.insurance.assured.common.enums.InsuranceCategory
import com.insurance.assured.common.extensions.load
import com.insurance.assured.databinding.*
import kotlin.math.roundToInt

class HomeAdapter :
    ListAdapter<HomeListItem, RecyclerView.ViewHolder>(MainDiffUtil()) {

    private var callBack: CallBack? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            HomeListItem.ViewType.MAIN_BANNERS.ordinal -> {
                MainBannerViewHolder(
                    LayoutBannerPagerBinding.inflate(layoutInflater, parent, false)
                )
            }

            HomeListItem.ViewType.CATEGORIES.ordinal -> {
                CategoriesViewHolder(
                    LayoutCategoriesItemBinding.inflate(layoutInflater, parent, false)
                )
            }

            HomeListItem.ViewType.HOT_BANNER.ordinal -> {
                HotBannerViewHolder(
                    PlanItemBinding.inflate(layoutInflater, parent, false)
                )
            }

            HomeListItem.ViewType.CASHLESS_BANNER.ordinal -> {
                CashlessBannerViewHolder(
                    LayoutCashlessBannerBinding.inflate(layoutInflater, parent, false)
                )
            }

            HomeListItem.ViewType.SHIMMER_BANNER.ordinal -> {
                ShimmerBannerViewHolder(
                    ShimmerBannerBinding.inflate(layoutInflater, parent, false)
                )
            }

            HomeListItem.ViewType.ERROR_MAIN_BANNER.ordinal -> {
                ErrorMainBannerViewHolder(
                    ErrorBannerBinding.inflate(layoutInflater, parent, false)
                )
            }

            HomeListItem.ViewType.ERROR_CAR_BANNER.ordinal -> {
                ErrorCarBannerViewHolder(
                    ErrorBannerBinding.inflate(layoutInflater, parent, false)
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
            is MainBannerViewHolder -> holder.bind(item as HomeListItem.MainBannersItem)
            is CategoriesViewHolder -> holder.bind()
            is HotBannerViewHolder -> holder.bind(item as HomeListItem.HotBannerItem)
            is CashlessBannerViewHolder -> holder.bind(item as HomeListItem.CashlessItem)
            is ShimmerBannerViewHolder -> holder.bind(item as HomeListItem.ShimmerBannerItem)
            is ErrorMainBannerViewHolder -> holder.bind(item as HomeListItem.ErrorMainBannerItem)
            is ErrorCarBannerViewHolder -> holder.bind(item as HomeListItem.ErrorCarBannerItem)
        }
    }


    inner class MainBannerViewHolder(private val binding: LayoutBannerPagerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val bannersAdapter = BannersPagerAdapter()

        init {
            binding.bannerViewpager.adapter = bannersAdapter
            binding.bannerViewpager.offscreenPageLimit = 1
        }

        fun bind(item: HomeListItem.MainBannersItem) {

            if (binding.bannerViewpager.itemDecorationCount > 0)
                binding.bannerViewpager.removeItemDecorationAt(0)

            val nextItemVisiblePx =
                binding.root.context.resources.getDimension(R.dimen.viewpager_next_item_visible)
            val currentItemHorizontalMarginPx =
                binding.root.context.resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin)
            val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
            val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
                page.translationX = -pageTranslationX * position
                page.scaleY = 1 - (0.25f * Math.abs(position))
            }
            binding.bannerViewpager.setPageTransformer(pageTransformer)

            val itemDecoration = HorizontalMarginItemDecoration(
                binding.root.context,
                R.dimen.viewpager_current_item_horizontal_margin
            )
            binding.bannerViewpager.addItemDecoration(itemDecoration)
            bannersAdapter.submitList(item.banners)
        }
    }

    inner class CategoriesViewHolder(private val binding: LayoutCategoriesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            binding.houseLayout.setOnClickListener {
                callBack?.onCategoryItemClick(InsuranceCategory.HOUSE)
            }
            binding.healthLayout.setOnClickListener {
                callBack?.onCategoryItemClick(InsuranceCategory.HEALTH)
            }
            binding.vehicleLayout.setOnClickListener {
                callBack?.onCategoryItemClick(InsuranceCategory.VEHICLE)
            }
            binding.petLayout.setOnClickListener {
                callBack?.onCategoryItemClick(InsuranceCategory.PET)
            }
        }
    }

    inner class HotBannerViewHolder(private val binding: PlanItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: HomeListItem.HotBannerItem) {
            with(binding){
                buyBtn.text = "${item.monthlyPayment.roundToInt()}$/month"
                categoryIcon.setBackgroundResource(item.category.icon)
                slogan.text = item.slogan
                icon.load(item.image)
                title.text = item.title
                maxMoney.text = item.totalMoney.toString()
                features.text = item.feats.joinToString(" | ")

                //model + boolean + shareViewModel

            }
        }
    }

    inner class CashlessBannerViewHolder(private val binding: LayoutCashlessBannerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HomeListItem.CashlessItem) {
            binding.root.setOnClickListener {
                callBack?.onItemClick(item.viewType)
            }
        }
    }

    inner class ShimmerBannerViewHolder(private val binding: ShimmerBannerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HomeListItem.ShimmerBannerItem) {
        }
    }

    inner class ErrorMainBannerViewHolder(private val binding: ErrorBannerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HomeListItem.ErrorMainBannerItem) {
            binding.root.setOnClickListener {
                callBack?.onItemClick(item.viewType)
            }
        }
    }

    inner class ErrorCarBannerViewHolder(private val binding: ErrorBannerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HomeListItem.ErrorCarBannerItem) {
            binding.root.setOnClickListener {
                callBack?.onItemClick(item.viewType)
            }
        }
    }


    interface CallBack {
        fun onItemClick(item: HomeListItem.ViewType)
        fun onCategoryItemClick(type: InsuranceCategory)
    }
}