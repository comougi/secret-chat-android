package com.ougi.messagingapi.data

import com.ougi.websocketapi.data.WebSocketState

interface MessagingFeatureClientApi {
    fun startMessagingWork(expedited: Boolean)
    fun observeState(onStateChanged: (WebSocketState) -> Unit)
    fun sendMessage(message: String)
}