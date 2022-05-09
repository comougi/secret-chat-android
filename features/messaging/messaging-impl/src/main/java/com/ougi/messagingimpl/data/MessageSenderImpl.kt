package com.ougi.messagingimpl.data

import com.ougi.messagingapi.data.MessageSender
import com.ougi.messagingapi.data.MessagingFeatureClientApi
import javax.inject.Inject

class MessageSenderImpl @Inject constructor(private val messageFeatureClientApi: MessagingFeatureClientApi) :
    MessageSender {

    fun sendMessage(message: String) {
        messageFeatureClientApi.sendMessage(message)
    }

}