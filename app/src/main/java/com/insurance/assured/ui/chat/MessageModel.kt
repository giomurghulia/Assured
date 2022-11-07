package com.insurance.assured.ui.chat

data class MessageModel(val message: String, val time: Long, val isUsersMessage: Boolean){
    constructor(): this("", 1, true)
}
