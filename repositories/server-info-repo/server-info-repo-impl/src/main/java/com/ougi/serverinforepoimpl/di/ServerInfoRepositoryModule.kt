package com.ougi.serverinforepoimpl.di

import com.ougi.coreutils.dagger.Repo
import com.ougi.networkapi.data.NetworkClientApi
import com.ougi.serverinforepoapi.data.network.ServerInfoRepositoryNetworkApi
import com.ougi.serverinforepoapi.data.repository.ServerInfoRepository
import com.ougi.serverinforepoimpl.data.network.ServerInfoRepositoryNetworkApiImpl
import com.ougi.serverinforepoimpl.data.network.ServerInfoRepositoryNetworkService
import com.ougi.serverinforepoimpl.data.repository.ServerInfoRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
internal interface ServerInfoRepositoryModule {

    @[Repo Binds]
    fun bindServerInfoRepositoryNetworkApi(serverInfoNetworkApiImpl: ServerInfoRepositoryNetworkApiImpl): ServerInfoRepositoryNetworkApi

    @[Repo Binds]
    fun bindServerInfoRepository(serverInfoRepositoryImpl: ServerInfoRepositoryImpl): ServerInfoRepository

    companion object {
        @[Repo Provides]
        fun provideServerInfoRepositoryNetworkService(networkClientApi: NetworkClientApi): ServerInfoRepositoryNetworkService {
            return networkClientApi.create(ServerInfoRepositoryNetworkService::class.java)
        }
    }
}