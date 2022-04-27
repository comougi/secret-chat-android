package com.ougi.websocketimpl.di

import com.ougi.coreutils.dagger.Feature
import com.ougi.websocketapi.di.WebSocketFeatureApi
import dagger.Component

@[Component(
    modules = [WebSocketFeatureModule::class],
    dependencies = [WebSocketFeatureDeps::class]
) Feature]
interface WebSocketFeatureComponent : WebSocketFeatureApi {
    companion object {
        fun newInstance(webSocketFeatureDeps: WebSocketFeatureDeps): WebSocketFeatureApi {
            return DaggerWebSocketFeatureComponent.builder()
                .webSocketFeatureDeps(webSocketFeatureDeps)
                .build()
        }
    }
}