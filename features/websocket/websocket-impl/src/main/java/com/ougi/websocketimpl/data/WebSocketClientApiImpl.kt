package com.ougi.websocketimpl.data

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.work.*
import com.ougi.corecommon.Config
import com.ougi.websocketapi.data.CustomWebSocketListener
import com.ougi.websocketapi.data.WebSocketClientApi
import com.ougi.websocketapi.data.WebSocketState
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class WebSocketClientApiImpl @Inject constructor(
    private val context: Context,
    private val webSocketListenerFactory: CustomWebSocketListener.Factory,
    private val okHttpClient: OkHttpClient
) : WebSocketClientApi {

    private val workManager by lazy { WorkManager.getInstance(context) }
    private val webSocketListener by lazy {
        webSocketListenerFactory.create { enqueueWebSocketWork(context) }
    }

    override fun connect(): CustomWebSocketListener {
        val wsConnectRequest = Request.Builder()
            .url(Config.BASE_WS_URL)
            .build()
        okHttpClient.newWebSocket(wsConnectRequest, webSocketListener)
        return webSocketListener
    }

    override fun observeWebSocketState(onStateChanged: (WebSocketState) -> Unit) {
        observeWebSocketWork { values ->
            val state = values[WebSocketWorker.STATE] as WebSocketState
            onStateChanged(state)
        }
    }

    override fun observeWebSocketMessages(onMessageReceived: (String) -> Unit) {
        observeWebSocketWork { values ->
            val message = values[WebSocketWorker.MESSAGE] as String
            onMessageReceived(message)
        }
    }

    private fun observeWebSocketWork(onProgressChanged: (Map<String, Any>) -> Unit) {
        workManager.getWorkInfosForUniqueWorkLiveData(WebSocketWorker.WEB_SOCKET_WORK_NAME)
            .observe(context as LifecycleOwner) { workInfos ->
                if (
                    !workInfos.isNullOrEmpty()
                    && workInfos.any { workInfo -> workInfo.state == WorkInfo.State.RUNNING }
                ) {
                    val workInfo = workInfos
                        .first { workInfo -> workInfo.state == WorkInfo.State.RUNNING }
                    val progressValues = workInfo.progress.keyValueMap
                    onProgressChanged(progressValues)
                } else {
                    enqueueWebSocketWork(context)
                }
            }
    }

    override fun enqueueWebSocketWork(context: Context) {
        val workConstraint = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val webSocketWorkRequest = OneTimeWorkRequest.Builder(WebSocketWorker::class.java)
            .setConstraints(workConstraint)
            .setInitialDelay(4000, TimeUnit.MILLISECONDS)
            .setBackoffCriteria(
                BackoffPolicy.LINEAR,
                OneTimeWorkRequest.MIN_BACKOFF_MILLIS,
                TimeUnit.MILLISECONDS
            )
            .build()
        WorkManager.getInstance(context).enqueueUniqueWork(
            WebSocketWorker.WEB_SOCKET_WORK_NAME,
            ExistingWorkPolicy.APPEND_OR_REPLACE,
            webSocketWorkRequest
        )
    }

}