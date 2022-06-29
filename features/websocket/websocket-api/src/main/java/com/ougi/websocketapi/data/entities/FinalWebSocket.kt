package com.ougi.websocketapi.data.entities

import okhttp3.WebSocket
import okhttp3.WebSocketListener

class FinalWebSocket(val webSocket: WebSocket, val listener: WebSocketListener)