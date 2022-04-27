package com.ougi.networkimpl.di

import com.ougi.networkapi.data.NetworkClientApi
import com.ougi.networkimpl.data.NetworkClientApiImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
internal interface CoreNetworkBindsModule {

    @[Singleton Binds]
    fun bindNetworkClientApi(networkClientApiImpl: NetworkClientApiImpl): NetworkClientApi

}