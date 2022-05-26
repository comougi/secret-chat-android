package com.ougi.messagingimpl.data

import android.content.Context
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

    override fun startMessagingWork(isInForeground: Boolean) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val workRequest = OneTimeWorkRequestBuilder<MessagingFeatureWorker>()
            .apply {
                addTag(MessagingFeatureWorker.WORK_NAME)
                setConstraints(constraints)
                setBackoffCriteria(BackoffPolicy.LINEAR, 5, TimeUnit.SECONDS)
                setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
                setInputData(workDataOf(IS_IN_FOREGROUND to isInForeground))
            }.build()

        workManager.enqueueUniqueWork(
            MessagingFeatureWorker.WORK_NAME,
            ExistingWorkPolicy.REPLACE,
            workRequest
        )
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

    private fun observeWork(onWorkInfosChanged: (List<WorkInfo>?) -> Unit) {
        workManager.getWorkInfosForUniqueWorkLiveData(MessagingFeatureWorker.WORK_NAME)
            .observeForever { workInfos ->
                onWorkInfosChanged(workInfos)
            }

    }

    private fun observeReceivingWork(onProgressChanged: (Map<String, Any>) -> Unit) {
        observeWork { workInfos ->
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

    companion object {
        const val IS_IN_FOREGROUND = "isInForeground"
    }
}