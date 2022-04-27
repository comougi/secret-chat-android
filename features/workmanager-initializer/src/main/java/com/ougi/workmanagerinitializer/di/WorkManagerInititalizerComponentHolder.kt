package com.ougi.workmanagerinitializer.di

import com.ougi.coreutils.dagger.ComponentHolder
import com.ougi.coreutils.dagger.ComponentHolderDelegate
import com.ougi.workmanagerinitializer.data.WorkManagerInitializerApi

object WorkManagerInititalizerComponentHolder :
    ComponentHolder<WorkManagerInitializerApi, WorkManagerInititalizerDeps> {

    private val componentHolderDelegate =
        ComponentHolderDelegate<WorkManagerInitializerApi, WorkManagerInititalizerDeps> { deps ->
            WorkManagerInititalizerComponent.newInstance(deps)
        }

    override var depsProvider: (() -> WorkManagerInititalizerDeps)? by componentHolderDelegate::depsProvider

    override fun getInstance(): WorkManagerInititalizerComponent =
        componentHolderDelegate.getInstance() as WorkManagerInititalizerComponent
}