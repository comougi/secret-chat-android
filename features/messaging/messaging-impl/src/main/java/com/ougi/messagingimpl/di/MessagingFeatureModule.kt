package com.ougi.messagingimpl.di

import com.ougi.coreutils.dagger.Feature
import com.ougi.encryptionapi.data.EncryptionClientApi
import com.ougi.messagerepoapi.data.repository.MessageRepository
import com.ougi.messagingapi.data.*
import com.ougi.messagingimpl.data.MessageReceiverImpl
import com.ougi.messagingimpl.data.MessageSenderImpl
import com.ougi.messagingimpl.data.MessagingFeatureClientApiImpl
import com.ougi.messagingimpl.data.SystemMessageHandlerImpl
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

    @[Feature Binds]
    fun bindSystemMessageHandler(systemMessageDefinerImpl: SystemMessageHandlerImpl): SystemMessageHandler

    companion object {
        @[Feature Provides]
        fun provideMessageSender(
            encryptionClientApi: EncryptionClientApi,
            messageRepository: MessageRepository
        ): MessageSender {
            return MessageSenderImpl.getInstance(encryptionClientApi, messageRepository)
        }
    }
}