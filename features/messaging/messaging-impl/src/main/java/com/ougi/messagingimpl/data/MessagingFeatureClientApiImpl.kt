package com.ougi.messagingimpl.data

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.work.*
import com.ougi.messagingapi.data.MessagingFeatureClientApi
import com.ougi.messagingimpl.data.workmanager.MessagingFeatureWorker
import com.ougi.websocketapi.data.entities.WebSocketState
import okhttp3.WebSocket
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MessagingFeatureClientApiImpl @Inject constructor(private val context: Context) :
    MessagingFeatureClientApi {

    private val workManager by lazy { WorkManager.getInstance(context) }

    override fun startMessagingWork(expedited: Boolean) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val workRequest = OneTimeWorkRequestBuilder<MessagingFeatureWorker>()
            .apply {
                addTag(MessagingFeatureWorker.WORK_NAME)
                setConstraints(constraints)
                setBackoffCriteria(BackoffPolicy.LINEAR, 5, TimeUnit.SECONDS)
                setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
                if (expedited) setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
                else setInitialDelay(5, TimeUnit.SECONDS)
            }.build()

        workManager.enqueue(workRequest)
    }

    override fun observeState(onStateChanged: (WebSocketState) -> Unit) {
        observeReceivingWork { values ->
            val state = values[MessagingFeatureWorker.STATE] as WebSocketState
            onStateChanged(state)
        }
    }

    override fun sendMessage(message: String) {
        observeReceivingWork { values ->
            val webSocket = values[MessagingFeatureWorker.WEB_SOCKET] as WebSocket
            webSocket.send(message)
        }
    }

    private fun observeReceivingWork(onProgressChanged: (Map<String, Any>) -> Unit) {
        workManager.getWorkInfosByTagLiveData(MessagingFeatureWorker.WORK_NAME)
            .observe(context as LifecycleOwner) { workInfos ->
                if (!workInfos.isNullOrEmpty()
                    && workInfos.any { info -> info.state == WorkInfo.State.RUNNING }
                ) {
                    val workInfo = workInfos
                        .first { workInfo -> workInfo.state == WorkInfo.State.RUNNING }
                    val progressValues = workInfo.progress.keyValueMap
                    onProgressChanged(progressValues)
                }
            }
    }
}