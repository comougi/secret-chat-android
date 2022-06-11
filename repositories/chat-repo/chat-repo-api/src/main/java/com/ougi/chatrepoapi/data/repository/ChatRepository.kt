package com.ougi.chatrepoapi.data.repository

import com.ougi.chatrepoapi.data.entity.Chat
import com.ougi.coreutils.utils.Result
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    suspend fun createChat(userIds: List<String>): Result<Chat?>
    fun getChats(): Flow<Result<List<Chat>?>>
    suspend fun getChatById(chatId: String): Chat?
    suspend fun insertChatToDatabase(chat: Chat)
}