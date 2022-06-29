package com.ougi.messagerepoapi.data.database

import com.ougi.messagerepoapi.data.entities.PersonalMessage
import kotlinx.coroutines.flow.Flow

interface PersonalMessageDatabaseDao {
    suspend fun getMessageById(id: String): PersonalMessage?
    fun getMessageByIdFlow(id: String): Flow<PersonalMessage>
    suspend fun updateMessage(message: PersonalMessage)
    suspend fun insertMessage(message: PersonalMessage)
    fun getChatMessagesFlow(chatId: String): Flow<List<PersonalMessage>>
    suspend fun deleteMessage(message: PersonalMessage)
}