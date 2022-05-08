package com.ougi.serverinforepoapi.di

import com.ougi.corecommon.base.di.BaseFeatureApi
import com.ougi.serverinforepoapi.data.ServerInfoDataStoreApi
import com.ougi.serverinforepoapi.data.ServerInfoNetworkApi

interface ServerInfoRepositoryApi : BaseFeatureApi {
    val serverInfoDataStoreApi: ServerInfoDataStoreApi
    val serverInfoNetworkApi: ServerInfoNetworkApi
}