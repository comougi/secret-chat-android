package com.ougi.messagingimpl.data.workmanager

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.ougi.messagerepoapi.data.entities.Message
import com.ougi.messagerepoapi.data.repository.MessageRepository
import com.ougi.messagingapi.data.MessageReceiver
import com.ougi.messagingapi.data.MessageSender
import com.ougi.messagingapi.data.MessagingFeatureClientApi
import com.ougi.messagingimpl.data.MessagingFeatureClientApiImpl.Companion.IS_IN_FOREGROUND
import com.ougi.serverinforepoapi.data.repository.ServerInfoRepository
import com.ougi.websocketapi.data.CustomWebSocketListener
import com.ougi.websocketapi.data.WebSocketClientApi
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MessagingFeatureWorker @AssistedInject constructor(
    private val serverInfoRepository: ServerInfoRepository,
    private val webSocketClientApi: WebSocketClientApi,
    private val messagingFeatureClientApi: MessagingFeatureClientApi,
    private val messageReceiver: MessageReceiver,
    private val messageSender: MessageSender,
    private val messageRepository: MessageRepository,
    @Assisted(CONTEXT) appContext: Context,
    @Assisted(PARAMS) params: WorkerParameters
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        return try {
            val isInForeground = inputData.getBoolean(IS_IN_FOREGROUND, false)
            val webSocketInfo =
                if (isInForeground) serverInfoRepository.getMessagingWebSocketConnectionLink()
                else serverInfoRepository.getPushWebSocketConnectionLink()
            when (webSocketInfo) {
                is com.ougi.coreutils.utils.Result.Success -> {
                    val link = webSocketInfo.data!!
                    val webSocket = webSocketClientApi.connect(link = link, onFailureDelay = 5000) {
                        messagingFeatureClientApi.startMessagingWork(isInForeground)
                    }
                    val webSocketListener = webSocket.listener as CustomWebSocketListener

                    CoroutineScope(Job()).launch {
                        webSocketListener.onMessageStateFlow.collect { message ->
                            if (message != null)
                                messageReceiver.receiveMessage(message)
                        }
                    }

                    CoroutineScope(Job()).launch {
                        messageSender.messages.collect { message ->
                            message?.let {
                                val encryptedMessage = it.second
                                val messageId = it.first
                                val send =
                                    webSocketListener.currentWebSocket?.send(encryptedMessage)
                                val messageStatus = when (send) {
                                    null -> Message.Status.FAIL
                                    true -> Message.Status.SENT
                                    false -> Message.Status.FAIL
                                }
                                Log.d("DATA", messageStatus.toString())
                                messageRepository.updateMessageStatus(messageId, messageStatus)
                            }
                        }
                    }

                    webSocketListener.webSocketStateStateFlow.collect { state ->
                        setProgress(workDataOf(STATE to state.name))
                    }

                }
                else -> {
                    Log.d("DATA", webSocketInfo.message() ?: "web socket error")
                    Result.retry()
                }

            }
        } catch (e: Exception) {
            Log.d("DATA", e.stackTraceToString())
            Result.retry()
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted(CONTEXT) appContext: Context,
            @Assisted(PARAMS) params: WorkerParameters
        ): MessagingFeatureWorker
    }

    companion object {
        private const val CONTEXT = "context"
        private const val PARAMS = "params"
        const val WORK_NAME = "MessagingWork"
        const val STATE = "state"
        const val WEB_SOCKET = "FinalWebSocket"
    }
}