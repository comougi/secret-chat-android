package com.ougi.messagerepoimpl.di

import com.ougi.coreutils.dagger.Feature
import com.ougi.dbapi.data.DbClientApi
import com.ougi.messagerepoapi.data.database.PersonalMessageDatabaseDao
import com.ougi.messagerepoapi.data.database.SystemMessageDatabaseDao
import com.ougi.messagerepoapi.data.repository.MessageRepository
import com.ougi.messagerepoimpl.data.database.MessageRepositoryDatabase
import com.ougi.messagerepoimpl.data.repository.MessageRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface MessageRepositoryModule {

    @[Feature Binds]
    fun bindMessageRepository(messageRepositoryImpl: MessageRepositoryImpl): MessageRepository

    companion object {

        @[Feature Provides]
        fun provideMessageRepositoryDatabase(databaseClientApi: DbClientApi): MessageRepositoryDatabase {
            return MessageRepositoryDatabase.getDatabaseClient(databaseClientApi)
        }

        @[Feature Provides]
        fun providePersonalMessageDatabaseDao(messageRepositoryDatabase: MessageRepositoryDatabase): PersonalMessageDatabaseDao {
            return messageRepositoryDatabase.personalMessageDatabaseDao
        }

        @[Feature Provides]
        fun provideSystemMessageDatabaseDao(messageRepositoryDatabase: MessageRepositoryDatabase): SystemMessageDatabaseDao {
            return messageRepositoryDatabase.systemMessageDatabaseDao
        }

    }
}