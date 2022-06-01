package com.ougi.chatlistscreenimpl.di

import com.ougi.chatrepoapi.data.database.ChatRepositoryDao
import com.ougi.corecommon.base.di.BaseFeatureDeps
import com.ougi.messagingapi.data.MessagingFeatureClientApi

interface ChatListScreenDeps : BaseFeatureDeps {
    val chatRepositoryDao: ChatRepositoryDao
    val messagingFeatureClientApi: MessagingFeatureClientApi
}