package com.ougi.dbimpl.di

import com.ougi.corecommon.base.di.BaseFeatureDeps
import com.ougi.coreutils.utils.ContextProvider

interface CoreDbDeps : BaseFeatureDeps {
    val contextProvider: ContextProvider
}