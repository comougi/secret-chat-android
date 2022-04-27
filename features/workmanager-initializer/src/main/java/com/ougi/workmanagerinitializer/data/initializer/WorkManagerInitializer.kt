package com.ougi.workmanagerinitializer.data.initializer

import android.content.Context
import androidx.work.Configuration
import androidx.work.WorkManager
import com.ougi.workmanagerinitializer.data.factory.CustomWorkerFactory
import javax.inject.Inject

interface WorkManagerInitializer {
    fun initialize()
}

class WorkManagerInitializerImpl @Inject constructor(
    private val context: Context,
    private val workerFactory: CustomWorkerFactory
) : WorkManagerInitializer {
    override fun initialize() {
        val config = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
        WorkManager.initialize(context, config)
    }

}