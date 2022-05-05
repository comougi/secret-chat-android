package com.ougi.datastoreapi.di

import com.ougi.corecommon.base.di.BaseFeatureApi
import com.ougi.datastoreapi.data.DataStoreClientApi

interface DataStoreFeatureApi : BaseFeatureApi {
    val dataStoreClientApi: DataStoreClientApi
}