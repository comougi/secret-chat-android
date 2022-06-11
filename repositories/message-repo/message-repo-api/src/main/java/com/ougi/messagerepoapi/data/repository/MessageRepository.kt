package com.ougi.messagerepoapi.data.repository

import com.ougi.messagerepoapi.data.entities.Message
import com.ougi.messagerepoapi.data.entities.SystemMessage

interface MessageRepository {

    suspend fun getSystemMessages(): List<SystemMessage>?

    suspend fun saveMessage(message: Message)
    suspend fun deleteMessage(message: Message)

    suspend fun updateMessageStatus(messageId: String, status: Message.Status)

    fun encryptMessageData(data: String, publicKey: String): String
    suspend fun decryptMessageData(encryptedData: String): Pair<String, Boolean>

}