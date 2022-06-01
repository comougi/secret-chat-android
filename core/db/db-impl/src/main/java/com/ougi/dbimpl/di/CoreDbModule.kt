package com.ougi.dbimpl.di

import com.ougi.dbapi.data.DbClientApi
import com.ougi.dbimpl.data.DbClientApiImpl
import dagger.Binds
import dagger.Module

@Module
internal interface CoreDbModule {

    @Binds
    fun bindDbClientApi(dbClientApiImpl: DbClientApiImpl): DbClientApi

}