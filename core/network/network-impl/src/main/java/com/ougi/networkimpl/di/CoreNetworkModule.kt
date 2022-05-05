package com.ougi.networkimpl.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.ougi.corecommon.Config
import com.ougi.networkapi.data.NetworkClientApi
import com.ougi.networkimpl.data.NetworkClientApiImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
internal interface CoreNetworkModule {

    @[Singleton Binds]
    fun bindNetworkClientApi(networkClientApiImpl: NetworkClientApiImpl): NetworkClientApi

    companion object {
        @OptIn(ExperimentalSerializationApi::class)
        @[Singleton Provides]
        fun provideRetrofit(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(Config.BASE_URL)
                .addConverterFactory(Json.asConverterFactory(MediaType.get("application/json")))
                .build()
        }
    }
}