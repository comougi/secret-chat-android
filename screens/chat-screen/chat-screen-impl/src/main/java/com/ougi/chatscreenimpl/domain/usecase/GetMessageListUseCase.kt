package com.ougi.chatscreenimpl.domain.usecase

import com.ougi.coreutils.utils.Result
import com.ougi.messagerepoapi.data.entities.PersonalMessage
import com.ougi.messagerepoapi.data.repository.MessageRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.flow.onStart

interface GetMessageListUseCase {
    val personalMessagesResult: Flow<Result<List<PersonalMessage>>>

    interface Factory {
        fun create(chatId: String): GetMessageListUseCase
    }
}

class GetMessageListUseCaseImpl @AssistedInject constructor(
    messageRepository: MessageRepository,
    @Assisted(CHAT_ID) chatId: String
) : GetMessageListUseCase {

    override val personalMessagesResult: Flow<Result<List<PersonalMessage>>> =
        messageRepository.personalMessagesByChatId(chatId)
            .map { list ->
                if (list.isNotEmpty()) Result.Success(list)
                else Result.Error(list)
            }
            .onStart { emit(Result.Loading()) }
            .onEmpty { emit(Result.Error()) }

    @AssistedFactory
    interface Factory : GetMessageListUseCase.Factory {
        override fun create(@Assisted(CHAT_ID) chatId: String): GetMessageListUseCaseImpl
    }

    companion object {
        private const val CHAT_ID = "chatId"
    }
}