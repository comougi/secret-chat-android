package com.ougi.coreutils.dagger

import com.ougi.corecommon.base.di.BaseFeatureApi
import com.ougi.corecommon.base.di.BaseFeatureDeps

interface ComponentHolder<A : BaseFeatureApi, D : BaseFeatureDeps> {
    var depsProvider: (() -> D)?
    fun getInstance(): A
}

