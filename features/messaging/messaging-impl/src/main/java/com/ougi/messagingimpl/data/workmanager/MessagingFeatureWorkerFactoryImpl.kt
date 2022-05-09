package com.ougi.messagingimpl.data.workmanager

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import com.ougi.messagingapi.data.MessagingFeatureWorkerFactory
import javax.inject.Inject

class MessagingFeatureWorkerFactoryImpl @Inject constructor(private val factory: MessageReceiverWorker.Factory) :
    MessagingFeatureWorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker {
        return factory.create(appContext, workerParameters)
    }
}