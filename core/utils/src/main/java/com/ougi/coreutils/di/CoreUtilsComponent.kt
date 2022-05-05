package com.ougi.coreutils.di

import dagger.Component
import javax.inject.Singleton

@[Component(
    modules = [CoreUtilsModule::class],
    dependencies = [CoreUtilsDeps::class]
) Singleton]
interface CoreUtilsComponent : CoreUtilsApi {

    companion object {
        fun newInstance(coreUtilsDeps: CoreUtilsDeps): CoreUtilsApi {
            return DaggerCoreUtilsComponent.builder()
                .coreUtilsDeps(coreUtilsDeps)
                .build()
        }
    }
}