package com.ougi.coreutils.di

import com.ougi.corecommon.base.di.BaseFeatureDeps
import com.ougi.coreutils.utils.ContextProvider

interface CoreUtilsDeps : BaseFeatureDeps {
    val contextProvider: ContextProvider
}