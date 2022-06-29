package com.ougi.chatrepoapi.data.database

import com.ougi.chatrepoapi.data.entity.Chat
import kotlinx.coroutines.flow.Flow

interface ChatDatabaseDao {
    suspend fun getAllChats(): List<Chat>
    fun getAllChatsFlow(): Flow<List<Chat>>
    suspend fun getChatById(chatId: String): Chat?
    fun getChatByIdFlow(chatId: String): Flow<Chat>
    suspend fun insertChat(chat: Chat)
    suspend fun deleteChatById(chatId: String)
    suspend fun deleteChat(chat: Chat)
}