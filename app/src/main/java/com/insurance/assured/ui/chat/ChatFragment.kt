package com.insurance.assured.ui.chat

import androidx.fragment.app.viewModels
import com.insurance.assured.databinding.FragmentChatBinding
import com.insurance.assured.ui.basefragments.BaseFragment


class ChatFragment : BaseFragment<FragmentChatBinding>(
    FragmentChatBinding::inflate
) {
    private val viewModel: ChatViewModel by viewModels()
    private val adapter = ChatAdapter()

    override fun init() {}

    override fun observe() {}

    override fun listener() {
        binding.sendImage.setOnClickListener {
            val message = binding.messageEdittext.text?.toString()
            if (!message.isNullOrEmpty()){

            }

        }
    }
}