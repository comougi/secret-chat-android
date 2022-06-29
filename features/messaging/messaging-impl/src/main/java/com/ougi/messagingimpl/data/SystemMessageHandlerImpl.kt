package com.ougi.messagingimpl.data

import android.util.Log
import com.ougi.chatrepoapi.data.entity.Chat
import com.ougi.chatrepoapi.data.repository.ChatRepository
import com.ougi.messagerepoapi.data.entities.Message
import com.ougi.messagerepoapi.data.entities.SystemMessage
import com.ougi.messagerepoapi.data.repository.MessageRepository
import com.ougi.messagingapi.data.SystemMessageHandler
import com.ougi.userrepoapi.data.repository.UserRepository
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class SystemMessageHandlerImpl @Inject constructor(
    private val messageRepository: MessageRepository,
    private val chatRepository: ChatRepository,
    private val userRepository: UserRepository
) : SystemMessageHandler {

    override suspend fun handleAll() {
        val systemMessages = messageRepository.getSystemMessages()
        if (!systemMessages.isNullOrEmpty()) {
            systemMessages.forEach { message ->
                handle(message)
            }
        }
    }

    override suspend fun handle(message: SystemMessage) {
        val decryptedMessageData = messageRepository.decryptMessageData(message.data)
        val messageData = Json.decodeFromString<SystemMessage.Data>(decryptedMessageData.first)
        when (messageData.type) {
            SystemMessage.Data.Type.MESSAGE_DELIVERED -> handleMessageDelivered(messageData)
            SystemMessage.Data.Type.CHAT_ADDED -> handleChatAdded(messageData)
        }
        messageRepository.deleteMessage(message)
    }

    private suspend fun handleMessageDelivered(data: SystemMessage.Data) {
        val deliveredMessageId = data.data
        messageRepository.updateMessageStatus(deliveredMessageId, Message.Status.DELIVERED)
        Log.d("DATA", "handleMessageDelivered")
    }

    private suspend fun handleChatAdded(data: SystemMessage.Data) {
        val chat = Json.decodeFromString<Chat>(data.data)
        val userId = userRepository.getUserId()
        val filteredUsersChat = Chat(
            id = chat.id,
            title = chat.title,
            users = chat.users.filter { user -> user.id != userId }
        )
        chatRepository.insertChatToDatabase(filteredUsersChat)
        Log.d("DATA", "handleChatAdded")
    }

}