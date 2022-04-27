package com.ougi.websocketapi.data

import kotlinx.coroutines.flow.StateFlow
import okhttp3.WebSocketListener

abstract class CustomWebSocketListener : WebSocketListener() {
    abstract val webSocketStateStateFlow: StateFlow<WebSocketState>
    abstract val onMessageStateFlow: StateFlow<String?>

    interface Factory {
        fun create(onFailure: () -> Unit): CustomWebSocketListener
    }
}