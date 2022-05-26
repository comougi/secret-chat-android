package com.ougi.messagingapi.data

import com.ougi.websocketapi.data.entities.WebSocketState

interface MessagingFeatureClientApi {
    fun startMessagingWork(isInForeground: Boolean)
    fun observeState(onStateChanged: (WebSocketState) -> Unit)
    fun sendMessage(message: String)
}