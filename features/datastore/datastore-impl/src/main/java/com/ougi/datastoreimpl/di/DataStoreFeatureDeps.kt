package com.ougi.datastoreimpl.di

import android.content.Context
import com.ougi.corecommon.base.di.BaseFeatureDeps

interface DataStoreFeatureDeps : BaseFeatureDeps {
    val context: Context
}