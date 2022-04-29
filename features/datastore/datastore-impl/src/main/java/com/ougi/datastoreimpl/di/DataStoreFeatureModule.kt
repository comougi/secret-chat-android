package com.ougi.datastoreimpl.di

import com.ougi.coreutils.dagger.Feature
import com.ougi.datastoreapi.data.DataStoreClientApi
import com.ougi.datastoreimpl.data.DataStoreClientApiImpl
import dagger.Binds
import dagger.Module

@Module
interface DataStoreFeatureModule {

    @[Feature Binds]
    fun bindDataStoreClientApi(dataStoreClientApiImpl: DataStoreClientApiImpl): DataStoreClientApi

}