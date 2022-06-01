package com.ougi.chatlistscreenimpl.data

import com.ougi.chatlistscreenimpl.domain.repository.ChatListRepository
import com.ougi.chatrepoapi.data.database.ChatRepositoryDao
import com.ougi.chatrepoapi.data.entity.Chat
import com.ougi.coreutils.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEmpty
import javax.inject.Inject

class ChatListRepositoryImpl @Inject constructor(chatRepositoryDao: ChatRepositoryDao) :
    ChatListRepository {

    override val chatsResultFlow: Flow<Result<List<Chat>?>> =
        chatRepositoryDao.getAllChatsFlow()
            .map { list -> Result.Success(list) as Result<List<Chat>?> }
            .catch { emit(Result.Error()) }
            .onEmpty { emit(Result.Loading()) }

}