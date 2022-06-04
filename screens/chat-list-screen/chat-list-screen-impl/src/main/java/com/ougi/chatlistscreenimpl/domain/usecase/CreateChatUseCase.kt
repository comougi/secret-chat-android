package com.ougi.chatlistscreenimpl.domain.usecase

import com.ougi.chatrepoapi.data.database.ChatRepositoryDao
import com.ougi.chatrepoapi.data.entity.Chat
import com.ougi.chatrepoapi.data.repository.ChatRepository
import com.ougi.coreutils.utils.Result
import com.ougi.userrepoapi.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

interface CreateChatUseCase {
    val hasUserResultStateFlow: StateFlow<Result<Boolean?>>
    val createChatResultStateFlow: StateFlow<Result<Chat?>>
    suspend fun isUserRegistered(id: String)
    suspend fun createChat(userIds: List<String>)
}

class CreateChatUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository,
    private val chatRepository: ChatRepository,
    private val chatRepositoryDao: ChatRepositoryDao
) :
    CreateChatUseCase {

    override val hasUserResultStateFlow: MutableStateFlow<Result<Boolean?>> =
        MutableStateFlow(Result.Loading())

    override val createChatResultStateFlow: MutableStateFlow<Result<Chat?>> =
        MutableStateFlow(Result.Loading())

    override suspend fun isUserRegistered(id: String) {
        hasUserResultStateFlow.value = Result.Loading()
        hasUserResultStateFlow.value = userRepository.isUserRegistered(id)
    }

    override suspend fun createChat(userIds: List<String>) {
        createChatResultStateFlow.value = Result.Loading()
        val createChatResult = chatRepository.createChat(userIds)
        createChatResultStateFlow.value = createChatResult
        if (createChatResult is Result.Success) {
            saveChat(createChatResult.data!!)
        }
    }

    private suspend fun saveChat(chat: Chat) {
        chatRepositoryDao.insertChat(chat)
    }
}