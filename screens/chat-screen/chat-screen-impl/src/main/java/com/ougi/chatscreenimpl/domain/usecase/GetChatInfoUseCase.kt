package com.ougi.chatscreenimpl.domain.usecase

import com.ougi.chatrepoapi.data.repository.ChatRepository
import javax.inject.Inject

interface GetChatInfoUseCase {
    suspend fun getChatTitle(chatId: String): String?
}

class GetChatInfoUseCaseImpl @Inject constructor(private val chatRepository: ChatRepository) :
    GetChatInfoUseCase {

    override suspend fun getChatTitle(chatId: String): String? {
        return chatRepository.getChatById(chatId)?.title
    }

}