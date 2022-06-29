package com.ougi.messagingimpl.di

import com.ougi.coreutils.dagger.ComponentHolder
import com.ougi.coreutils.dagger.ComponentHolderDelegate
import com.ougi.messagingapi.di.MessagingFeatureApi

object MessagingFeatureComponentHolder :
    ComponentHolder<MessagingFeatureApi, MessagingFeatureDeps> {

    private val componentHolderDelegate =
        ComponentHolderDelegate<MessagingFeatureApi, MessagingFeatureDeps> { deps ->
            MessagingFeatureComponent.newInstance(deps)
        }

    override var depsProvider: (() -> MessagingFeatureDeps)? by componentHolderDelegate::depsProvider

    override fun getInstance(): MessagingFeatureComponent =
        componentHolderDelegate.getInstance() as MessagingFeatureComponent
}