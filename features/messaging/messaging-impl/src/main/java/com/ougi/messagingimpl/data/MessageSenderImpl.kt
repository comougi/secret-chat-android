package com.ougi.messagingimpl.data

import com.ougi.messagingapi.data.MessageSender
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class MessageSenderImpl @Inject constructor() : MessageSender {

    override val messages: MutableStateFlow<String?> = MutableStateFlow(null)

    override fun sendMessage(message: String) {
        messages.value = message
    }

    companion object {
        private var INSTANCE: MessageSender? = null
        fun getInstance(): MessageSender {
            return INSTANCE ?: MessageSenderImpl()
                .also { sender -> INSTANCE = sender }
        }
    }
}