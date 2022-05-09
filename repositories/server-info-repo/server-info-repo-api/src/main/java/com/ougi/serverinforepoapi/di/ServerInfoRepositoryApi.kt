package com.ougi.serverinforepoapi.di

import com.ougi.corecommon.base.di.BaseFeatureApi
import com.ougi.serverinforepoapi.data.datastore.ServerInfoRepositoryDataStoreApi
import com.ougi.serverinforepoapi.data.network.ServerInfoRepositoryNetworkApi
import com.ougi.serverinforepoapi.data.repository.ServerInfoRepository

interface ServerInfoRepositoryApi : BaseFeatureApi {
    val serverInfoRepositoryDataStoreApi: ServerInfoRepositoryDataStoreApi
    val serverInfoRepositoryNetworkApi: ServerInfoRepositoryNetworkApi
    val serverInfoRepository: ServerInfoRepository
}