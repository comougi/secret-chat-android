package com.ougi.networkimpl.di

import com.ougi.networkapi.di.CoreNetworkApi
import dagger.Component
import javax.inject.Singleton

@[Component(
    modules = [
        CoreNetworkBindsModule::class,
        CoreNetworkProvidesModule::class
    ],
    dependencies = [
        CoreNetworkDeps::class
    ]
) Singleton]
interface CoreNetworkComponent : CoreNetworkApi {

    companion object {
        fun newInstance(coreNetworkDeps: CoreNetworkDeps): CoreNetworkApi {
            return DaggerCoreNetworkComponent.builder()
                .coreNetworkDeps(coreNetworkDeps)
                .build()
        }
    }
}