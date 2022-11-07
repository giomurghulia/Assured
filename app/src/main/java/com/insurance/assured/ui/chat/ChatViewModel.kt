package com.insurance.assured.ui.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.insurance.assured.ui.pages.home.HomeListItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class ChatViewModel : ViewModel() {


    private val database = Firebase.database
    private val usersDatabaseReference =
        database.getReference("giomailru")
    private val _chatListStateFlow = MutableStateFlow<List<ChatListItem>>(emptyList())
     val chatListStateFlow = _chatListStateFlow.asStateFlow()
    private var listNow = mutableListOf<MessageModel>()

    private fun buildList(chat: List<MessageModel>) {
        val list = mutableListOf<ChatListItem>()

        chat.forEach {
            if (it.isUsersMessage) {
                list.add(ChatListItem.UserMessageItem(it.message, getDate(it.time, "h:mm a")))
            } else {
                list.add(ChatListItem.AdminMessageItem(it.message, getDate(it.time, "h:mm a")))
            }
        }

        _chatListStateFlow.value = list

    }

    fun sendMessage(message: String) {
        viewModelScope.launch {
            usersDatabaseReference.get().addOnSuccessListener {
                listNow = try {
                    it.value as MutableList<MessageModel>
                } catch (e: Exception) {
                    return@addOnSuccessListener
                }
                listNow.add(MessageModel(message, System.currentTimeMillis(), true))
                usersDatabaseReference.setValue(listNow)
            }.addOnCanceledListener {}
        }
    }

    fun getNewMessages() {
        usersDatabaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                listNow = dataSnapshot.getValue<MutableList<MessageModel>>() ?: listNow
                buildList(listNow)
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                //Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
    }

    private fun getDate(epochSec: Long, dataFormatSr: String): String {
        val date = Date(epochSec * 1000)
        val format = SimpleDateFormat(dataFormatSr, Locale.getDefault())

        return format.format(date)
    }
}