package com.ougi.chatlistscreenimpl.domain.usecase

import com.ougi.chatrepoapi.data.entity.Chat
import com.ougi.chatrepoapi.data.repository.ChatRepository
import com.ougi.coreutils.utils.Result
import com.ougi.messagerepoapi.data.entities.Message
import com.ougi.messagerepoapi.data.entities.SystemMessage
import com.ougi.messagingapi.data.MessageSender
import com.ougi.userrepoapi.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
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
    private val messageSender: MessageSender,
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
            val chat = createChatResult.data!!
            val userId = userRepository.getUserId()

            val filteredUsersChat = Chat(
                id = chat.id,
                title = chat.title,
                users = chat.users.filter { user -> user.id != userId }
            )
            chatRepository.insertChatToDatabase(filteredUsersChat)
            filteredUsersChat.users.forEach { user ->

                val messageData = SystemMessage.Data(
                    SystemMessage.Data.Type.CHAT_ADDED,
                    Json.encodeToString(chat)
                )

                val message = SystemMessage(
                    senderId = userId,
                    recipientId = user.id,
                    chatId = chat.id,
                    data = Json.encodeToString(messageData),
                    type = Message.Type.SYSTEM
                )
                messageSender.sendMessage(message, user.rsaPublicKey)
            }
        }

    }

}