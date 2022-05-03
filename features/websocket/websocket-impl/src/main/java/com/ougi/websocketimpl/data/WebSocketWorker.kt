package com.ougi.websocketimpl.data

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.ougi.websocketapi.data.WebSocketClientApi
import com.ougi.websocketapi.data.WebSocketWorkerFactory
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class WebSocketWorker @AssistedInject constructor(
    private val webSocketClientApi: WebSocketClientApi,
    @Assisted(CONTEXT) appContext: Context,
    @Assisted(PARAMS) params: WorkerParameters
) :
    CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        val webSocketListener = webSocketClientApi.connect()

        coroutineScope {
            launch {
                webSocketListener.onMessageStateFlow.collect { message ->
                    setProgress(workDataOf(MESSAGE to message))
                }
            }
        }

        webSocketListener.webSocketStateStateFlow.collect { state ->
            setProgress(workDataOf(STATE to state))
            setProgress(workDataOf(WEB_SOCKET to webSocketListener.currentWebSocket))
        }
    }


    @AssistedFactory
    interface Factory {
        fun create(@Assisted(CONTEXT) context: Context, @Assisted(PARAMS) params: WorkerParameters)
                : WebSocketWorker
    }

    class WebSocketWorkerFactoryImpl @Inject constructor(private val factory: Factory) :
        WebSocketWorkerFactory() {
        override fun createWorker(
            appContext: Context, workerClassName: String, workerParameters: WorkerParameters
        ): ListenableWorker {
            return factory.create(appContext, workerParameters)
        }
    }

    companion object {
        private const val CONTEXT = "context"
        private const val PARAMS = "params"
        const val WEB_SOCKET = "web_socket"
        const val MESSAGE = "message"
        const val STATE = "state"
        const val WEB_SOCKET_WORK_NAME = "WebSocketWork"
    }
}