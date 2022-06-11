package com.ougi.messagerepoimpl.data.database

import androidx.room.Database
import androidx.room.TypeConverters
import com.ougi.dbapi.data.BaseDatabase
import com.ougi.dbapi.data.DbClientApi
import com.ougi.messagerepoapi.data.entities.PersonalMessage
import com.ougi.messagerepoapi.data.entities.SystemMessage

@[
Database(
    entities = [PersonalMessage::class, SystemMessage::class],
    exportSchema = false,
    version = 1
)
TypeConverters(MessageRepositoryDatabaseTypeConverter::class)
]
abstract class MessageRepositoryDatabase : BaseDatabase() {
    abstract val personalMessageDatabaseDao: PersonalMessageDatabaseDaoImpl
    abstract val systemMessageDatabaseDao: SystemMessageDatabaseDaoImpl

    companion object {
        private var INSTANCE: MessageRepositoryDatabase? = null
        fun getDatabaseClient(dbClientApi: DbClientApi): MessageRepositoryDatabase {
            return INSTANCE
                ?: (dbClientApi.provideDatabase(MessageRepositoryDatabase::class.java) as MessageRepositoryDatabase)
                    .also { database -> INSTANCE = database }
        }
    }
}