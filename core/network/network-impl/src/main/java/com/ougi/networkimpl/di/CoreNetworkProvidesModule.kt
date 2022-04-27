package com.ougi.networkimpl.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.ougi.corecommon.Config
import dagger.Module
import dagger.Provides
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
internal class CoreNetworkProvidesModule {

    @OptIn(ExperimentalSerializationApi::class)
    @[Singleton Provides]
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Config.BASE_URL)
            .addConverterFactory(Json.asConverterFactory(MediaType.get("application/json")))
            .build()
    }

}