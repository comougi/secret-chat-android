package com.ougi.websocketimpl.data

import android.util.Log
import com.ougi.websocketapi.data.CustomWebSocketListener
import com.ougi.websocketapi.data.entities.WebSocketState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okhttp3.Response
import okhttp3.WebSocket

class CustomWebSocketListenerImpl @AssistedInject constructor(
    @Assisted(ON_FAILURE) private val onFailure: () -> Unit,
    @Assisted(ON_FAILURE_DELAY) private val onFailureDelay: Long
) : CustomWebSocketListener() {

    override var currentWebSocket: WebSocket? = null

    override val webSocketStateStateFlow: MutableStateFlow<WebSocketState> =
        MutableStateFlow(WebSocketState.CONNECTING)

    override val onMessageStateFlow: MutableStateFlow<String?> = MutableStateFlow(null)

    override fun onOpen(webSocket: WebSocket, response: Response) {
        super.onOpen(webSocket, response)
        currentWebSocket = webSocket
        Log.d(TAG, "onOpen")
        webSocketStateStateFlow.value = WebSocketState.CONNECTED
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        super.onMessage(webSocket, text)
        currentWebSocket = webSocket
        onMessageStateFlow.value = text
        Log.d(TAG, "onMessage : $text")
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosed(webSocket, code, reason)
        currentWebSocket = webSocket
        Log.d(TAG, "onClosed")
        webSocketStateStateFlow.value = WebSocketState.CLOSED
        onFailure()
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        super.onFailure(webSocket, t, response)
        currentWebSocket = webSocket
        Log.d(TAG, "onFailure")
        webSocketStateStateFlow.value = WebSocketState.CLOSED
        CoroutineScope(Job()).launch {
            delay(5000)
            onFailure()
        }
    }

    @AssistedFactory
    interface Factory : CustomWebSocketListener.Factory {
        override fun create(
            @Assisted(ON_FAILURE) onFailure: () -> Unit,
            @Assisted(ON_FAILURE_DELAY) onFailureDelay: Long
        ): CustomWebSocketListenerImpl
    }

    companion object {
        private const val TAG = "WebSocketListener"
        private const val ON_FAILURE = "onFailure"
        private const val ON_FAILURE_DELAY = "onFailureDelay"

    }
}