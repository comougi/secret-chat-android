package com.ougi.chatrepoapi.data.repository

import com.ougi.chatrepoapi.data.entity.Chat
import com.ougi.coreutils.utils.Result

interface ChatRepository {
    suspend fun createChat(userIds: List<String>): Result<Chat?>
}