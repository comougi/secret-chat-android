package com.ougi.serverinforepoimpl.di

import com.ougi.coreutils.dagger.Repo
import com.ougi.networkapi.data.NetworkClientApi
import com.ougi.serverinforepoapi.data.ServerInfoDataStoreApi
import com.ougi.serverinforepoapi.data.ServerInfoNetworkApi
import com.ougi.serverinforepoimpl.data.datastore.ServerInfoDataStoreApiImpl
import com.ougi.serverinforepoimpl.data.network.ServerInfoNetworkApiImpl
import com.ougi.serverinforepoimpl.data.network.ServerInfoNetworkService
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
internal interface ServerInfoRepositoryModule {

    @[Repo Binds]
    fun bindServerInfoDataStoreApi(serverInfoDataStoreImpl: ServerInfoDataStoreApiImpl): ServerInfoDataStoreApi

    @[Repo Binds]
    fun bindServerInfoNetworkApiImpl(serverInfoNetworkApiImpl: ServerInfoNetworkApiImpl): ServerInfoNetworkApi

    companion object {
        @[Repo Provides]
        fun provideServerInfoNetworkService(networkClientApi: NetworkClientApi): ServerInfoNetworkService {
            return networkClientApi.create(ServerInfoNetworkService::class.java)
        }
    }
}