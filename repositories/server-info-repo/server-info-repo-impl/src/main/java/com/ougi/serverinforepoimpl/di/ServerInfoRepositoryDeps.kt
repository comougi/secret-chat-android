package com.ougi.serverinforepoimpl.di

import android.content.Context
import com.ougi.corecommon.base.di.BaseFeatureDeps
import com.ougi.datastoreapi.data.DataStoreClientApi
import com.ougi.networkapi.data.NetworkClientApi

interface ServerInfoRepositoryDeps : BaseFeatureDeps {
    val context: Context
    val dataStoreClientApi: DataStoreClientApi
    val networkClientApi: NetworkClientApi
}