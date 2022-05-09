package com.ougi.websocketimpl.data

import com.ougi.websocketapi.data.CustomWebSocketListener
import com.ougi.websocketapi.data.WebSocketClientApi
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import javax.inject.Inject


class WebSocketClientApiImpl @Inject constructor(
    private val webSocketListenerFactory: CustomWebSocketListener.Factory,
    private val okHttpClient: OkHttpClient
) : WebSocketClientApi {

//    private val workManager by lazy { WorkManager.getInstance(context) }

    override fun connect(link: String, onFailure: () -> Unit): WebSocket {
        val wsConnectRequest = Request.Builder()
            .url(link)
            .build()
        val webSocketListener = getWebSocketListener(onFailure)
        return okHttpClient.newWebSocket(wsConnectRequest, webSocketListener)
    }

    override fun sendMessage(webSocket: WebSocket, message: String): Boolean {
        return webSocket.send(message)
    }

    private fun getWebSocketListener(onFailure: () -> Unit): CustomWebSocketListener {
        return webSocketListenerFactory.create(onFailure)
    }

//    override fun sendMessage(message: String) {
//        observeWebSocketWork { values ->
//            val webSocket = values[WebSocketWorker.WEB_SOCKET] as WebSocket
//            webSocket.send(message)
//        }
//    }

//    override fun observeWebSocketState(onStateChanged: (WebSocketState) -> Unit) {
//        observeWebSocketWork { values ->
//            val state = values[WebSocketWorker.STATE] as WebSocketState
//            onStateChanged(state)
//        }
//    }
//
//    override fun observeWebSocketMessages(onMessageReceived: (String) -> Unit) {
//        observeWebSocketWork { values ->
//            val message = values[WebSocketWorker.MESSAGE] as String
//            onMessageReceived(message)
//        }
//    }
//
//    private fun observeWebSocketWork(onProgressChanged: (Map<String, Any>) -> Unit) {
//        workManager.getWorkInfosForUniqueWorkLiveData(WebSocketWorker.WEB_SOCKET_WORK_NAME)
//            .observe(context as LifecycleOwner) { workInfos ->
//                if (
//                    !workInfos.isNullOrEmpty()
//                    && workInfos.any { workInfo -> workInfo.state == WorkInfo.State.RUNNING }
//                ) {
//                    val workInfo = workInfos
//                        .first { workInfo -> workInfo.state == WorkInfo.State.RUNNING }
//                    val progressValues = workInfo.progress.keyValueMap
//                    onProgressChanged(progressValues)
//                } else {
//                    enqueueWebSocketWork(context)
//                }
//            }
//    }
//
//    override fun enqueueWebSocketWork(context: Context) {
//        val workConstraint = Constraints.Builder()
//            .setRequiredNetworkType(NetworkType.CONNECTED)
//            .build()
//        val webSocketWorkRequest = OneTimeWorkRequest.Builder(WebSocketWorker::class.java)
//            .setConstraints(workConstraint)
//            .setInitialDelay(4000, TimeUnit.MILLISECONDS)
//            .setBackoffCriteria(
//                BackoffPolicy.LINEAR,
//                OneTimeWorkRequest.MIN_BACKOFF_MILLIS,
//                TimeUnit.MILLISECONDS
//            )
//            .build()
//        WorkManager.getInstance(context).enqueueUniqueWork(
//            WebSocketWorker.WEB_SOCKET_WORK_NAME,
//            ExistingWorkPolicy.APPEND_OR_REPLACE,
//            webSocketWorkRequest
//        )
//    }

}