package com.ougi.secretchat.di

import com.ougi.secretchat.data.MainActivityStarter
import com.ougi.secretchat.data.MainActivityStarterImpl
import dagger.Binds
import dagger.Module

@Module
internal interface AppModule {

    @[Binds]
    fun bindMainActivityStarter(mainActivityStarterImpl: MainActivityStarterImpl): MainActivityStarter

}