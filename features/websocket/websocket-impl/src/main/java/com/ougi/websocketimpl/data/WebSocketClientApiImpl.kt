package com.ougi.websocketimpl.data

import com.ougi.websocketapi.data.CustomWebSocketListener
import com.ougi.websocketapi.data.WebSocketClientApi
import com.ougi.websocketapi.data.entities.FinalWebSocket
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import javax.inject.Inject


class WebSocketClientApiImpl @Inject constructor(
    private val webSocketListenerFactory: CustomWebSocketListener.Factory,
    private val okHttpClient: OkHttpClient
) : WebSocketClientApi {

    override fun connect(link: String, onFailure: () -> Unit): FinalWebSocket {
        val wsConnectRequest = Request.Builder()
            .url(link)
            .build()
        val webSocketListener = getWebSocketListener(onFailure)
        val webSocket = okHttpClient.newWebSocket(wsConnectRequest, webSocketListener)
        return FinalWebSocket(webSocket, webSocketListener)
    }

    override fun sendMessage(webSocket: WebSocket, message: String): Boolean {
        return webSocket.send(message)
    }

    private fun getWebSocketListener(onFailure: () -> Unit): CustomWebSocketListener {
        return webSocketListenerFactory.create(onFailure)
    }

}