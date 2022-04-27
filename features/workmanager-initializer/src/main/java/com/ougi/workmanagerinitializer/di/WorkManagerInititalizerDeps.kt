package com.ougi.workmanagerinitializer.di

import android.content.Context
import com.ougi.corecommon.base.di.BaseFeatureDeps
import com.ougi.websocketapi.data.WebSocketWorkerFactory

interface WorkManagerInititalizerDeps : BaseFeatureDeps {
    val context: Context
    val webSocketWorkerFactory: WebSocketWorkerFactory
}