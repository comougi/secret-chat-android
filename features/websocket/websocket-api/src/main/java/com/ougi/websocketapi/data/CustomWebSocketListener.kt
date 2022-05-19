package com.ougi.websocketapi.data

import com.ougi.websocketapi.data.entities.WebSocketState
import kotlinx.coroutines.flow.StateFlow
import okhttp3.WebSocket
import okhttp3.WebSocketListener

abstract class CustomWebSocketListener : WebSocketListener() {
    abstract var currentWebSocket: WebSocket?
    abstract val webSocketStateStateFlow: StateFlow<WebSocketState>
    abstract val onMessageStateFlow: StateFlow<String?>

    interface Factory {
        fun create(onFailure: () -> Unit): CustomWebSocketListener
    }
}