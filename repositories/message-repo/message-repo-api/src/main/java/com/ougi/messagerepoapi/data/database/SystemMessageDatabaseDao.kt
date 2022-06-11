package com.ougi.messagerepoapi.data.database

import com.ougi.messagerepoapi.data.entities.SystemMessage

interface SystemMessageDatabaseDao {
    suspend fun getAllSystemMessages(): List<SystemMessage>?
    suspend fun getMessageById(id: String): SystemMessage?
    suspend fun updateMessage(message: SystemMessage)
    suspend fun insertMessage(message: SystemMessage)
    suspend fun deleteMessage(message: SystemMessage)
    suspend fun deleteMessageById(id: String)
}