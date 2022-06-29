package com.ougi.messagingimpl.data

import android.util.Log
import com.ougi.chatrepoapi.data.entity.Chat
import com.ougi.chatrepoapi.data.repository.ChatRepository
import com.ougi.messagerepoapi.data.entities.Message
import com.ougi.messagerepoapi.data.entities.SystemMessage
import com.ougi.messagerepoapi.data.repository.MessageRepository
import com.ougi.messagingapi.data.SystemMessageHandler
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class SystemMessageHandlerImpl @Inject constructor(
    private val messageRepository: MessageRepository,
    private val chatRepository: ChatRepository
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
        when (message.data.type) {
            SystemMessage.Data.Type.MESSAGE_DELIVERED -> handleMessageDelivered(message)
            SystemMessage.Data.Type.CHAT_ADDED -> handleChatAdded(message)
        }
        messageRepository.deleteMessage(message)
    }

    private suspend fun handleMessageDelivered(message: SystemMessage) {
        val deliveredMessageId = message.data.data
        messageRepository.updateMessageStatus(deliveredMessageId, Message.Status.DELIVERED)
        Log.d("DATA", "handleMessageDelivered")
    }

    private suspend fun handleChatAdded(message: SystemMessage) {
        val chat = Json.decodeFromString<Chat>(message.data.data)
        chatRepository.insertChatToDatabase(chat)
        Log.d("DATA", "handleChatAdded")
    }

}