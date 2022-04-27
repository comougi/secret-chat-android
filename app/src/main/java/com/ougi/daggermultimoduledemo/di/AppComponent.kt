package com.ougi.daggermultimoduledemo.di

import com.ougi.daggermultimoduledemo.data.AppApi
import dagger.Component
import javax.inject.Singleton

@[Component(
    modules = [
        AppProvidesModule::class,
        AppBindsModule::class
    ],
    dependencies = [
        AppDeps::class
    ]
) Singleton]
interface AppComponent : AppApi {

    companion object {
        fun newInstance(appDeps: AppDeps): AppApi {
            return DaggerAppComponent.builder()
                .appDeps(appDeps)
                .build()
        }
    }
}