package com.ougi.messagingapi.di

import com.ougi.corecommon.base.di.BaseFeatureApi
import com.ougi.messagingapi.data.MessageReceiver
import com.ougi.messagingapi.data.MessageSender
import com.ougi.messagingapi.data.MessagingFeatureClientApi
import com.ougi.messagingapi.data.MessagingFeatureWorkerFactory

interface MessagingFeatureApi : BaseFeatureApi {
    val messagingFeatureClientApi: MessagingFeatureClientApi
    val messagingFeatureWorkerFactory: MessagingFeatureWorkerFactory
    val messageReceiver: MessageReceiver
    val messageSender: MessageSender
}