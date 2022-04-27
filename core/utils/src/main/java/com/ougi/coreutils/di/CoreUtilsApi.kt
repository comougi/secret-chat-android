package com.ougi.coreutils.di

import com.ougi.corecommon.base.di.BaseFeatureApi
import com.ougi.coreutils.utils.ContextProvider

interface CoreUtilsApi : BaseFeatureApi {
    val contextProvider: ContextProvider
}