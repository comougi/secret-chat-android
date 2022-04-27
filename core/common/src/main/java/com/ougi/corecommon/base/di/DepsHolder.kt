package com.ougi.corecommon.base.di

interface DepsHolder<D : BaseFeatureDeps> {
    val depsFactory: (DepsHolder<D>) -> D
    val deps: D get() = depsFactory(this)
}