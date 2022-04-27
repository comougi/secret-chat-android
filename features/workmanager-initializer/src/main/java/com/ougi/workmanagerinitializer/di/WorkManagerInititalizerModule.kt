package com.ougi.workmanagerinitializer.di

import com.ougi.coreutils.dagger.Feature
import com.ougi.workmanagerinitializer.data.initializer.WorkManagerInitializer
import com.ougi.workmanagerinitializer.data.initializer.WorkManagerInitializerImpl
import dagger.Binds
import dagger.Module

@Module
interface WorkManagerInititalizerModule {

    @[Feature Binds]
    fun bindWorkManagerInitializer(workManagerInitializerImpl: WorkManagerInitializerImpl): WorkManagerInitializer

}