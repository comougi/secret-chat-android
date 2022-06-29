package com.ougi.workmanagerinitializer.di

import androidx.work.WorkerFactory
import com.ougi.coreutils.dagger.Feature
import com.ougi.messagingapi.data.MessagingFeatureWorkerFactory
import com.ougi.workmanagerinitializer.data.initializer.WorkManagerInitializer
import com.ougi.workmanagerinitializer.data.initializer.WorkManagerInitializerImpl
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet

@Module
interface WorkManagerInititalizerModule {

    @[Feature Binds]
    fun bindWorkManagerInitializer(workManagerInitializerImpl: WorkManagerInitializerImpl): WorkManagerInitializer

    //add factories here
    @[Feature Binds IntoSet]
    fun bindMessagingFeatureWorkerFactory(factory: MessagingFeatureWorkerFactory): WorkerFactory

}