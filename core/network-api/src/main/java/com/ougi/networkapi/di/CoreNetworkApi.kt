package com.ougi.networkapi.di

import com.ougi.corecommon.base.di.BaseFeatureApi
import com.ougi.networkapi.data.NetworkClientApi

interface CoreNetworkApi : BaseFeatureApi {
    val networkClientApi: NetworkClientApi
}