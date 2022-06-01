package com.ougi.messagingapi.data

import kotlinx.coroutines.flow.StateFlow

interface MessageSender {
    val messages: StateFlow<String?>
    fun sendMessage(message: String)
}