package com.ougi.chatlistscreenimpl.di

import com.ougi.chatrepoapi.data.repository.ChatRepository
import com.ougi.corecommon.base.di.BaseFeatureDeps
import com.ougi.messagingapi.data.MessagingFeatureClientApi
import com.ougi.userrepoapi.data.repository.UserRepository

interface ChatListScreenDeps : BaseFeatureDeps {
    val messagingFeatureClientApi: MessagingFeatureClientApi
    val userRepository: UserRepository
    val chatRepository: ChatRepository
}