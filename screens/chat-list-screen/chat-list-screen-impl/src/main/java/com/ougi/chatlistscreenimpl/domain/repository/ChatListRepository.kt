package com.ougi.chatlistscreenimpl.domain.repository

import com.ougi.chatrepoapi.data.entity.Chat
import com.ougi.coreutils.utils.Result
import kotlinx.coroutines.flow.Flow

interface ChatListRepository {
    val chatsResultFlow: Flow<Result<List<Chat>?>>
}