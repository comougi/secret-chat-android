package com.ougi.messagingimpl.di

import com.ougi.coreutils.dagger.Feature
import com.ougi.messagingapi.data.MessageReceiver
import com.ougi.messagingapi.data.MessageSender
import com.ougi.messagingapi.data.MessagingFeatureClientApi
import com.ougi.messagingapi.data.MessagingFeatureWorkerFactory
import com.ougi.messagingimpl.data.MessageReceiverImpl
import com.ougi.messagingimpl.data.MessageSenderImpl
import com.ougi.messagingimpl.data.MessagingFeatureClientApiImpl
import com.ougi.messagingimpl.data.workmanager.MessagingFeatureWorkerFactoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface MessagingFeatureModule {

    @[Feature Binds]
    fun bindMessageReceiver(messageReceiverImpl: MessageReceiverImpl): MessageReceiver

    @[Feature Binds]
    fun bindMessagingFeatureClientApi(messagingFeatureClientApiImpl: MessagingFeatureClientApiImpl): MessagingFeatureClientApi

    @[Feature Binds]
    fun bindMessagingFeatureWorkerFactory(factory: MessagingFeatureWorkerFactoryImpl): MessagingFeatureWorkerFactory

    companion object {
        @[Feature Provides]
        fun provideMessageSender(): MessageSender {
            return MessageSenderImpl.getInstance()
        }
    }
}