package com.ougi.secretchat.di

import com.ougi.secretchat.data.AppApi
import dagger.Component
import javax.inject.Singleton

@[Component(
    modules = [AppModule::class],
    dependencies = [AppDeps::class]
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