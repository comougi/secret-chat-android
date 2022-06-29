package com.ougi.workmanagerinitializer.data.factory

import androidx.work.DelegatingWorkerFactory
import androidx.work.WorkerFactory
import javax.inject.Inject

class CustomWorkerFactory @Inject constructor(factories: Set<@JvmSuppressWildcards WorkerFactory>) :
    DelegatingWorkerFactory() {
    init {
        factories.forEach { factory ->
            addFactory(factory)
        }
    }
}