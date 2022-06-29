package com.ougi.messagingapi.di

import com.ougi.corecommon.base.di.BaseFeatureApi
import com.ougi.messagingapi.data.*

interface MessagingFeatureApi : BaseFeatureApi {
    val messagingFeatureClientApi: MessagingFeatureClientApi
    val messagingFeatureWorkerFactory: MessagingFeatureWorkerFactory
    val messageReceiver: MessageReceiver
    val messageSender: MessageSender
    val systemMessageHandler: SystemMessageHandler
}