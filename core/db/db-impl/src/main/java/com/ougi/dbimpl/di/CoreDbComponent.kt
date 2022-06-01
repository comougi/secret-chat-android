package com.ougi.dbimpl.di

import com.ougi.dbapi.di.CoreDbApi
import dagger.Component
import javax.inject.Singleton

@[Component(
    modules = [CoreDbModule::class],
    dependencies = [CoreDbDeps::class]
) Singleton]
interface CoreDbComponent : CoreDbApi {

    companion object {

        fun newInstance(coreDbDeps: CoreDbDeps): CoreDbApi {
            return DaggerCoreDbComponent.builder()
                .coreDbDeps(coreDbDeps)
                .build()
        }

    }

}