package com.ougi.messagingimpl.data.workmanager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.ougi.messagingapi.data.MessageReceiver
import com.ougi.messagingapi.data.MessagingFeatureClientApi
import com.ougi.serverinforepoapi.data.repository.ServerInfoRepository
import com.ougi.websocketapi.data.CustomWebSocketListener
import com.ougi.websocketapi.data.WebSocketClientApi
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class MessageReceiverWorker @AssistedInject constructor(
    private val serverInfoRepository: ServerInfoRepository,
    private val webSocketClientApi: WebSocketClientApi,
    private val messagingFeatureClientApi: MessagingFeatureClientApi,
    private val messageReceiver: MessageReceiver,
    @Assisted(CONTEXT) appContext: Context,
    @Assisted(PARAMS) params: WorkerParameters
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        return when (val webSocketInfo = serverInfoRepository.getWebSocketInfo()) {
            is com.ougi.coreutils.utils.Result.Success -> {
                val link = webSocketInfo.data!!.wsLink
                val webSocket = webSocketClientApi.connect(link) {
                    messagingFeatureClientApi.startMessagingWork(true)
                }
                val webSocketListener = webSocket as CustomWebSocketListener

                coroutineScope {
                    launch {
                        webSocketListener.onMessageStateFlow.collect { message ->
                            if (message != null)
                                messageReceiver.receiveMessage(message)
                        }
                    }
                }

                webSocketListener.webSocketStateStateFlow.collect { state ->
                    setProgress(workDataOf(STATE to state))
                    setProgress(workDataOf(WEB_SOCKET to webSocketListener.currentWebSocket))
                }

            }
            else -> Result.retry()
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted(CONTEXT) appContext: Context,
            @Assisted(PARAMS) params: WorkerParameters
        ): MessageReceiverWorker
    }

    companion object {
        private const val CONTEXT = "context"
        private const val PARAMS = "params"
        const val WORK_NAME = "MessageReceiver"
        const val STATE = "state"
        const val WEB_SOCKET = "WebSocket"
    }
}