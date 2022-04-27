package com.ougi.websocketimpl.data

import android.util.Log
import com.ougi.websocketapi.data.CustomWebSocketListener
import com.ougi.websocketapi.data.WebSocketState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import okhttp3.Response
import okhttp3.WebSocket

class CustomWebSocketListenerImpl @AssistedInject constructor(
    @Assisted(ON_FAILURE) private val onFailure: () -> Unit
) : CustomWebSocketListener() {
    override val webSocketStateStateFlow: MutableStateFlow<WebSocketState> =
        MutableStateFlow(WebSocketState.CONNECTING)

    override val onMessageStateFlow: MutableStateFlow<String?> = MutableStateFlow(null)

    override fun onOpen(webSocket: WebSocket, response: Response) {
        super.onOpen(webSocket, response)
        Log.d(TAG, "onOpen")
        webSocketStateStateFlow.value = WebSocketState.CONNECTED
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        super.onMessage(webSocket, text)
        onMessageStateFlow.value = text
        Log.d("WS_DATA", text)
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosed(webSocket, code, reason)
        Log.d(TAG, "onClosed")
        webSocketStateStateFlow.value = WebSocketState.CLOSED
        onFailure()
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        super.onFailure(webSocket, t, response)
        Log.d(TAG, "onFailure")
        webSocketStateStateFlow.value = WebSocketState.CLOSED
        onFailure()
    }

    @AssistedFactory
    interface Factory : CustomWebSocketListener.Factory {
        override fun create(@Assisted(ON_FAILURE) onFailure: () -> Unit): CustomWebSocketListenerImpl
    }

    companion object {
        private const val TAG = "WebSocketListener"
        private const val ON_FAILURE = "onFailure"
    }
}