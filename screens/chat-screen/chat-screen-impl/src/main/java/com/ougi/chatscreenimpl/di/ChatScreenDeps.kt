package com.ougi.chatscreenimpl.di

import com.ougi.chatrepoapi.data.repository.ChatRepository
import com.ougi.corecommon.base.di.BaseFeatureDeps
import com.ougi.encryptionapi.data.KeyStorageApi
import com.ougi.messagerepoapi.data.database.PersonalMessageDatabaseDao
import com.ougi.messagerepoapi.data.repository.MessageRepository
import com.ougi.messagingapi.data.MessageSender
import com.ougi.userrepoapi.data.repository.UserRepository

interface ChatScreenDeps : BaseFeatureDeps {
    val messageSender: MessageSender
    val userRepository: UserRepository
    val chatRepository: ChatRepository
    val messageDatabaseDao: PersonalMessageDatabaseDao
    val keyStorageApi: KeyStorageApi
    val messageRepository: MessageRepository
}