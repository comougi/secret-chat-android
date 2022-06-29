package com.ougi.coreutils.di

import com.ougi.coreutils.data.ContextProviderImpl
import com.ougi.coreutils.utils.ContextProvider
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface CoreUtilsModule {

    @[Singleton Binds]
    fun bindContextProvider(contextProviderImpl: ContextProviderImpl): ContextProvider

}