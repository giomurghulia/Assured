package com.insurance.assured.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.insurance.assured.common.extensions.load
import com.insurance.assured.databinding.LayoutBannerItemBinding


class BannersPagerAdapter : RecyclerView.Adapter<BannersPagerAdapter.ViewHolder>() {

    private var banners = emptyList<Banners>()


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
    fun submitList(cards: List<Banners>) {
        this.banners = cards
        notifyDataSetChanged()
    }

    inner class ViewHolder(
        private val binding: LayoutBannerItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Banners) {
            binding.bannerImage.load(item.banner)
            binding.titleText.text = item.title

        }
    }

}