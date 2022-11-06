package com.insurance.assured.ui.pages.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.insurance.assured.databinding.LayoutBannerItemBinding


class BannersPagerAdapter : RecyclerView.Adapter<BannersPagerAdapter.ViewHolder>() {

    private var banners = emptyList<Banner>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            LayoutBannerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
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