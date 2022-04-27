package com.ougi.workmanagerinitializer.data.factory

import androidx.work.DelegatingWorkerFactory
import javax.inject.Inject

class CustomWorkerFactory @Inject constructor() : DelegatingWorkerFactory() {
    init {
        //add factories here
    }
}