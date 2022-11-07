package com.insurance.assured.ui.chat

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.insurance.assured.databinding.FragmentChatBinding
import com.insurance.assured.ui.basefragments.BaseFragment
import kotlinx.coroutines.launch


class ChatFragment : BaseFragment<FragmentChatBinding>(
    FragmentChatBinding::inflate
) {
    private val viewModel: ChatViewModel by viewModels()
    private val adapter = ChatAdapter()

    override fun init() {
        viewModel.getNewMessages()
    }

    override fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.chatListStateFlow.collect {
                    adapter.submitList(it.toList())
                }
            }
        }
    }

    override fun listener() {
        binding.sendImage.setOnClickListener {
            val message = binding.messageEdittext.text?.toString()
            if (!message.isNullOrEmpty()) {
                viewModel.sendMessage(message)
            }

        }
    }
}