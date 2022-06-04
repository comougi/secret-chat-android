package com.ougi.messagingimpl.data

import android.content.Context
import androidx.lifecycle.asFlow
import androidx.work.*
import com.ougi.messagingapi.data.MessagingFeatureClientApi
import com.ougi.messagingimpl.data.workmanager.MessagingFeatureWorker
import com.ougi.websocketapi.data.entities.WebSocketState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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

    override fun webSocketStateAsFlow(): Flow<WebSocketState?> =
        runningWorkFlow()
            .map { workInfo ->
                workInfo?.let { info ->
                    val values = info.progress.keyValueMap
                    (values[MessagingFeatureWorker.STATE] as String?)
                        ?.let { state -> WebSocketState.valueOf(state) }
                }
            }

    private fun workInfosFlow(): Flow<List<WorkInfo>?> =
        workManager.getWorkInfosForUniqueWorkLiveData(MessagingFeatureWorker.WORK_NAME).asFlow()

    private fun runningWorkFlow(): Flow<WorkInfo?> =
        workInfosFlow()
            .map { workInfos ->
                if (workInfos?.any { info -> info.state == WorkInfo.State.RUNNING } == true)
                    workInfos.first { workInfo -> workInfo.state == WorkInfo.State.RUNNING }
                else
                    null
            }

    companion object {
        const val IS_IN_FOREGROUND = "isInForeground"
    }
}