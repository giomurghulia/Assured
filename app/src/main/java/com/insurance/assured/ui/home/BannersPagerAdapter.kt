package com.insurance.assured.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.insurance.assured.common.extensions.load
import com.insurance.assured.databinding.LayoutBannerItemBinding


class BannersPagerAdapter : RecyclerView.Adapter<BannersPagerAdapter.ViewHolder>() {

    private var banners = emptyList<Banner>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BannersPagerAdapter.ViewHolder {
        return ViewHolder(
            LayoutBannerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: BannersPagerAdapter.ViewHolder, position: Int) {
        val item = banners[position]
        holder.bind(item)
    }


    override fun getItemCount(): Int {
        return banners.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(cards: List<Banner>) {
        this.banners = cards
        notifyDataSetChanged()
    }

    inner class ViewHolder(
        private val binding: LayoutBannerItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("CheckResult")
        fun bind(item: Banner) {
            Glide.with(binding.root.context)
                .load(item.banner)
                .transform(CenterInside(), RoundedCorners(25))
                .into(binding.bannerImage)

            binding.titleText.text = item.title

        }
    }

}