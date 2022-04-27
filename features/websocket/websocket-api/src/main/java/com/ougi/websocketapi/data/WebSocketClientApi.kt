package com.ougi.websocketapi.data

import android.content.Context

interface WebSocketClientApi {
    fun connect(): CustomWebSocketListener
    fun observeWebSocketWork(
        onStateChanged: (WebSocketState) -> Unit,
        onMessageReceived: (String) -> Unit
    )

    fun enqueueWebSocketWork(context: Context)
}