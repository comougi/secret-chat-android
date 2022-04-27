package com.ougi.websocketimpl.di

import com.ougi.coreutils.dagger.ComponentHolder
import com.ougi.coreutils.dagger.ComponentHolderDelegate
import com.ougi.websocketapi.di.WebSocketFeatureApi

object WebSocketFeatureComponentHolder :
    ComponentHolder<WebSocketFeatureApi, WebSocketFeatureDeps> {

    private val componentHolderDelegate =
        ComponentHolderDelegate<WebSocketFeatureApi, WebSocketFeatureDeps> { deps ->
            WebSocketFeatureComponent.newInstance(deps)
        }

    override var depsProvider: (() -> WebSocketFeatureDeps)? by componentHolderDelegate::depsProvider

    override fun getInstance(): WebSocketFeatureComponent =
        componentHolderDelegate.getInstance() as WebSocketFeatureComponent
}