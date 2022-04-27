package com.ougi.websocketimpl.di

import com.ougi.coreutils.dagger.Feature
import com.ougi.websocketapi.data.CustomWebSocketListener
import com.ougi.websocketapi.data.WebSocketClientApi
import com.ougi.websocketapi.data.WebSocketWorkerFactory
import com.ougi.websocketimpl.data.CustomWebSocketListenerImpl
import com.ougi.websocketimpl.data.WebSocketClientApiImpl
import com.ougi.websocketimpl.data.WebSocketWorker
import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

@Module
interface WebSocketFeatureModule {

    @[Feature Binds]
    fun bindWebSocketClientApi(webSocketClientApiImpl: WebSocketClientApiImpl): WebSocketClientApi

    @[Feature Binds]
    fun bindCustomWebSocketListenerFactory(factory: CustomWebSocketListenerImpl.Factory): CustomWebSocketListener.Factory

    @[Feature Binds]
    fun bindWebSocketWorkerFactory(webSocketWorkerFactoryImpl: WebSocketWorker.WebSocketWorkerFactoryImpl): WebSocketWorkerFactory

    companion object {
        @[Feature Provides]
        fun provideOkHttpClient(): OkHttpClient {
            return OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .callTimeout(30, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build()
        }
    }
}