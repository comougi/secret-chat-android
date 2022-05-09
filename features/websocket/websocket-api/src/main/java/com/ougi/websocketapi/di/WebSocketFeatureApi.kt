package com.ougi.websocketapi.di

import com.ougi.corecommon.base.di.BaseFeatureApi
import com.ougi.websocketapi.data.WebSocketClientApi

interface WebSocketFeatureApi : BaseFeatureApi {
    val webSocketClientApi: WebSocketClientApi
}