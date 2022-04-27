package com.ougi.websocketapi.di

import com.ougi.corecommon.base.di.BaseFeatureApi
import com.ougi.websocketapi.data.WebSocketClientApi
import com.ougi.websocketapi.data.WebSocketWorkerFactory

interface WebSocketFeatureApi : BaseFeatureApi {
    val webSocketWorkerFactory: WebSocketWorkerFactory
    val webSocketClientApi: WebSocketClientApi
}