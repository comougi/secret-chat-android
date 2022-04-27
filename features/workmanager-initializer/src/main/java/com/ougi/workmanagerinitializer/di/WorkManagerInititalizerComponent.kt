package com.ougi.workmanagerinitializer.di

import com.ougi.coreutils.dagger.Feature
import com.ougi.workmanagerinitializer.data.WorkManagerInitializerApi
import dagger.Component

@[Component(
    modules = [WorkManagerInititalizerModule::class],
    dependencies = [WorkManagerInititalizerDeps::class]
) Feature]
interface WorkManagerInititalizerComponent : WorkManagerInitializerApi {
    companion object {
        fun newInstance(workManagerInititalizerDeps: WorkManagerInititalizerDeps): WorkManagerInitializerApi {
            return DaggerWorkManagerInititalizerComponent.builder()
                .workManagerInititalizerDeps(workManagerInititalizerDeps)
                .build()
        }
    }
}