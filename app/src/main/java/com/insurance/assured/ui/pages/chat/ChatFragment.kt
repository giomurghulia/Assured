package com.insurance.assured.ui.pages.chat

import android.util.Log.d
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.insurance.assured.databinding.FragmentChatBinding
import com.insurance.assured.ui.basefragments.BaseFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class ChatFragment : BaseFragment<FragmentChatBinding>(
    FragmentChatBinding::inflate
) {
    private val viewModel: ChatViewModel by viewModels()
    private val adapter = ChatAdapter()

    override fun init() {
        viewModel.getNewMessages()
        binding.mainRecycler.adapter = adapter
        binding.mainRecycler.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, true)
    }

    override fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.chatListStateFlow.collect {
                    if (it.isNotEmpty())
                        adapter.submitList(it)

                    delay(300)
                    binding.mainRecycler.scrollToPosition(adapter.itemCount)

                }
            }
        }
    }

    override fun listener() {
        binding.sendImage.setOnClickListener {
            val message = binding.messageEdittext.text?.toString()
            if (!message.isNullOrEmpty()) {
                viewModel.sendMessage(message)
                binding.messageEdittext.setText("")
            }
        }

        binding.backImage.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }
}