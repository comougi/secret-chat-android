package com.ougi.websocketapi.data

import com.ougi.websocketapi.data.entities.FinalWebSocket
import okhttp3.WebSocket


interface WebSocketClientApi {
    fun connect(link: String, onFailure: () -> Unit): FinalWebSocket
    fun sendMessage(webSocket: WebSocket, message: String): Boolean
//    fun connect(): CustomWebSocketListener
//    fun sendMessage(message: String)
//    fun observeWebSocketState(onStateChanged: (WebSocketState) -> Unit)
//    fun observeWebSocketMessages(onMessageReceived: (String) -> Unit)
//    fun enqueueWebSocketWork(context: Context)
}