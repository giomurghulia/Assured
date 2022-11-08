package com.insurance.assured.ui.pages.chat

data class MessageModel(val message: String, val time: Long, val usersMessage: Boolean){
    constructor(): this("", 1, true)
}
