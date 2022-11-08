package com.insurance.assured.ui.pages.chat

import android.util.Log.d
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.insurance.assured.common.MainDiffUtil
import com.insurance.assured.common.enums.AuthEnum
import com.insurance.assured.common.extensions.load
import com.insurance.assured.databinding.*


class ChatAdapter :
    ListAdapter<ChatListItem, RecyclerView.ViewHolder>(MainDiffUtil()) {

    private var callBack: CallBack? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            ChatListItem.ViewType.USER_MESSAGE.ordinal -> {
                UserMessageViewHolder(
                    LayoutUserChatItemBinding.inflate(layoutInflater, parent, false)
                )
            }
            ChatListItem.ViewType.ADMIN_MESSAGE.ordinal -> {
                AdminMessageViewHolder(
                    LayoutAdminChatItemBinding.inflate(layoutInflater, parent, false)
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
        d("tato", position.toString())

        when (holder) {
            is UserMessageViewHolder -> holder.bind(item as ChatListItem.UserMessageItem)
            is AdminMessageViewHolder -> holder.bind(item as ChatListItem.AdminMessageItem)
        }
    }


    inner class UserMessageViewHolder(private val binding: LayoutUserChatItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ChatListItem.UserMessageItem) {
            binding.userMessageText.text = item.message
            binding.timeText.text = item.time
        }

    }

    inner class AdminMessageViewHolder(private val binding: LayoutAdminChatItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ChatListItem.AdminMessageItem) {
            binding.userMessageText.text = item.message
            binding.timeText.text = item.time
        }

    }


    interface CallBack {
    }
}