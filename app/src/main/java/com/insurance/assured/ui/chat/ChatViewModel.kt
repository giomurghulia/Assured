package com.insurance.assured.ui.chat

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ChatViewModel : ViewModel() {

    private val database = Firebase.database
    private val usersDatabaseReference =
        database.getReference(Firebase.auth.currentUser?.email ?: "")
    private val _chatListStateFlow = MutableStateFlow<List<MessageModel>>(emptyList())
    private val chatListStateFlow = _chatListStateFlow.asStateFlow()
    private var listNow = mutableListOf<MessageModel>()

    private fun buildList(list: List<MessageModel>) {

    }

    private fun sendMessage(message: String) {
        usersDatabaseReference.get().addOnSuccessListener {
            listNow = try {
                it.value as MutableList<MessageModel>
            } catch (e: Exception) {
                return@addOnSuccessListener
            }
            listNow.add(MessageModel(message, System.currentTimeMillis(), true))
            usersDatabaseReference.setValue(listNow)
        }.addOnCanceledListener {

        }

    }

    private fun getNewMessages() {
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
}

