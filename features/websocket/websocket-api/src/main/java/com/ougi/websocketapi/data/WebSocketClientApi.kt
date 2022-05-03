package com.ougi.websocketapi.data

import android.content.Context

interface WebSocketClientApi {
    fun connect(): CustomWebSocketListener
    fun sendMessage(message: String)
    fun observeWebSocketState(onStateChanged: (WebSocketState) -> Unit)
    fun observeWebSocketMessages(onMessageReceived: (String) -> Unit)
    fun enqueueWebSocketWork(context: Context)
}