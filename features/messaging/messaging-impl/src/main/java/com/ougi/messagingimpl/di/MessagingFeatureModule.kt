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

@Module
interface MessagingFeatureModule {

    @[Feature Binds]
    fun bindMessageReceiver(messageReceiverImpl: MessageReceiverImpl): MessageReceiver

    @[Feature Binds]
    fun bindMessageSender(messageSenderImpl: MessageSenderImpl): MessageSender

    @[Feature Binds]
    fun bindMessagingFeatureClientApiImpl(messagingFeatureClientApiImpl: MessagingFeatureClientApiImpl): MessagingFeatureClientApi

    @[Feature Binds]
    fun bindMessagingFeatureWorkerFactory(factory: MessagingFeatureWorkerFactoryImpl): MessagingFeatureWorkerFactory
}