package com.ougi.messagerepoapi.di

import com.ougi.corecommon.base.di.BaseFeatureApi
import com.ougi.messagerepoapi.data.database.PersonalMessageDatabaseDao
import com.ougi.messagerepoapi.data.database.SystemMessageDatabaseDao
import com.ougi.messagerepoapi.data.repository.MessageRepository

interface MessageRepositoryApi : BaseFeatureApi {
    val messageRepository: MessageRepository
    val personalMessageDatabaseDao: PersonalMessageDatabaseDao
    val systemMessageDatabaseDao: SystemMessageDatabaseDao
}