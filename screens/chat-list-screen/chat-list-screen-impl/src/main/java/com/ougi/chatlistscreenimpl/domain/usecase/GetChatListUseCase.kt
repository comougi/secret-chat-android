package com.ougi.chatlistscreenimpl.domain.usecase

import com.ougi.chatrepoapi.data.entity.Chat
import com.ougi.chatrepoapi.data.repository.ChatRepository
import com.ougi.coreutils.utils.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetChatListUseCase {
    val chatListResultFlow: Flow<Result<List<Chat>?>>
}

class GetChatListUseCaseImpl @Inject constructor(chatListRepository: ChatRepository) :
    GetChatListUseCase {

    override val chatListResultFlow: Flow<Result<List<Chat>?>> =
        chatListRepository.getChats()

}