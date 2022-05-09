package com.ougi.messagingimpl.di

import com.ougi.coreutils.dagger.Feature
import com.ougi.messagingapi.di.MessagingFeatureApi
import dagger.Component

@[Component(
    modules = [MessagingFeatureModule::class],
    dependencies = [MessagingFeatureDeps::class]
) Feature]
interface MessagingFeatureComponent : MessagingFeatureApi {
    companion object {
        fun newInstance(messagingFeatureDeps: MessagingFeatureDeps): MessagingFeatureApi {
            return DaggerMessagingFeatureComponent.builder()
                .messagingFeatureDeps(messagingFeatureDeps)
                .build()
        }
    }
}