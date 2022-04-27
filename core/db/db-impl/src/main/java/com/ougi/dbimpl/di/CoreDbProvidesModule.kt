package com.ougi.dbimpl.di

import android.content.Context
import com.ougi.coreutils.utils.ContextProvider
import dagger.Module
import dagger.Provides

@Module
internal class CoreDbProvidesModule {

    @Provides
    fun provideContext(contextProvider: ContextProvider): Context {
        return contextProvider.context
    }

}