package com.ougi.messagingimpl.di

import android.content.Context
import com.ougi.corecommon.base.di.BaseFeatureDeps
import com.ougi.serverinforepoapi.data.repository.ServerInfoRepository
import com.ougi.websocketapi.data.WebSocketClientApi

interface MessagingFeatureDeps : BaseFeatureDeps {
    val context: Context
    val serverInfoRepository: ServerInfoRepository
    val webSocketClientApi: WebSocketClientApi
}