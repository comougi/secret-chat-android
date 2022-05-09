package com.ougi.workmanagerinitializer.di

import android.content.Context
import com.ougi.corecommon.base.di.BaseFeatureDeps
import com.ougi.messagingapi.data.MessagingFeatureWorkerFactory

interface WorkManagerInititalizerDeps : BaseFeatureDeps {
    val context: Context
    val messagingFeatureWorkerFactory: MessagingFeatureWorkerFactory
}