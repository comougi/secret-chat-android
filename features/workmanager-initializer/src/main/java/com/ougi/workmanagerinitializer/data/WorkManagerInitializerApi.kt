package com.ougi.workmanagerinitializer.data

import com.ougi.corecommon.base.di.BaseFeatureApi
import com.ougi.workmanagerinitializer.data.initializer.WorkManagerInitializer

interface WorkManagerInitializerApi : BaseFeatureApi {
    val workManagerInitializer: WorkManagerInitializer
}