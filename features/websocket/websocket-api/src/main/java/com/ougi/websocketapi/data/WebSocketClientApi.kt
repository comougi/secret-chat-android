package com.ougi.websocketapi.data

import com.ougi.websocketapi.data.entities.FinalWebSocket
import okhttp3.WebSocket


interface WebSocketClientApi {
    fun connect(link: String, onFailureDelay: Long, onFailure: () -> Unit): FinalWebSocket
    fun sendMessage(webSocket: WebSocket, message: String): Boolean
}