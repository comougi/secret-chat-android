package com.ougi.workmanagerinitializer.data.factory

import androidx.work.DelegatingWorkerFactory
import com.ougi.messagingapi.data.MessagingFeatureWorkerFactory
import javax.inject.Inject

class CustomWorkerFactory @Inject constructor(
    messagingFeatureWorkerFactory: MessagingFeatureWorkerFactory
) : DelegatingWorkerFactory() {
    init {
        //add factories here
        addFactory(messagingFeatureWorkerFactory)
    }
}