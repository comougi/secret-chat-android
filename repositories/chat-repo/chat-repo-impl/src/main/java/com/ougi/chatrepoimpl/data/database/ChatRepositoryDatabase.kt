package com.ougi.chatrepoimpl.data.database

import androidx.room.Database
import androidx.room.TypeConverters
import com.ougi.chatrepoapi.data.entity.Chat
import com.ougi.dbapi.data.BaseDatabase
import com.ougi.dbapi.data.DbClientApi

@Database(entities = [Chat::class], exportSchema = false, version = 1)
@TypeConverters(ChatRepositoryDatabaseTypeConverter::class)
abstract class ChatRepositoryDatabase : BaseDatabase() {
    abstract val chatRepositoryDatabaseDao: ChatRepositoryDatabaseDao

    companion object {
        private var INSTANCE: ChatRepositoryDatabase? = null
        fun getDatabaseClient(dbClientApi: DbClientApi): ChatRepositoryDatabase {
            return INSTANCE
                ?: (dbClientApi.provideDatabase(ChatRepositoryDatabase::class.java) as ChatRepositoryDatabase)
                    .also { database -> INSTANCE = database }
        }
    }
}