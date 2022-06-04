package com.ougi.messagingapi.data

import com.ougi.websocketapi.data.entities.WebSocketState
import kotlinx.coroutines.flow.Flow

interface MessagingFeatureClientApi {
    fun startMessagingWork(isInForeground: Boolean)
    fun webSocketStateAsFlow(): Flow<WebSocketState?>
}