package com.ougi.messagingapi.data

import com.ougi.messagerepoapi.data.entities.Message
import kotlinx.coroutines.flow.StateFlow

interface MessageSender {
    val messages: StateFlow<Pair<String /* id */, String /* message */>?>
    suspend fun sendMessage(message: Message)
}