package com.ougi.workmanagerinitializer.data.factory

import androidx.work.DelegatingWorkerFactory
import com.ougi.websocketapi.data.WebSocketWorkerFactory
import javax.inject.Inject

class CustomWorkerFactory @Inject constructor(
    webSocketWorkerFactory: WebSocketWorkerFactory
) : DelegatingWorkerFactory() {
    init {
        //add factories here
        addFactory(webSocketWorkerFactory)
    }
}