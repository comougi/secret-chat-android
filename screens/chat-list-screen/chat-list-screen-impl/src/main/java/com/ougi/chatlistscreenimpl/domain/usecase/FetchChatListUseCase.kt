package com.ougi.chatlistscreenimpl.domain.usecase

import com.ougi.chatlistscreenimpl.domain.repository.ChatListRepository
import com.ougi.chatrepoapi.data.entity.Chat
import com.ougi.coreutils.utils.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface FetchChatListUseCase {
    val chatListResultFlow: Flow<Result<List<Chat>?>>
}

class FetchChatListUseCaseImpl @Inject constructor(chatListRepository: ChatListRepository) :
    FetchChatListUseCase {

    override val chatListResultFlow: Flow<Result<List<Chat>?>> =
        chatListRepository.chatsResultFlow


}