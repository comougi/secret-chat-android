package com.ougi.messagingimpl.data

import android.util.Log
import com.ougi.encryptionapi.data.EncryptionClientApi
import com.ougi.encryptionapi.data.entity.AesEncryptedData
import com.ougi.messagerepoapi.data.entities.Message
import com.ougi.messagerepoapi.data.entities.PersonalMessage
import com.ougi.messagerepoapi.data.entities.SystemMessage
import com.ougi.messagerepoapi.data.repository.MessageRepository
import com.ougi.messagingapi.data.MessageReceiver
import com.ougi.messagingapi.data.MessageSender
import com.ougi.messagingapi.data.SystemMessageHandler
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import javax.inject.Inject

class MessageReceiverImpl @Inject constructor(
    private val messageRepository: MessageRepository,
    private val encryptionClientApi: EncryptionClientApi,
    private val messageSender: MessageSender,
    private val systemMessageHandler: SystemMessageHandler
) : MessageReceiver {


    override suspend fun receiveMessage(messageText: String) {
        val messageJson = Json.decodeFromString<AesEncryptedData>(messageText)
        val decryptedMessage = encryptionClientApi.decryptViaDHAesKey(messageJson)

        val messageJsonMap = Json.parseToJsonElement(decryptedMessage.first)
        val messageType = Message.Type.valueOf(
            messageJsonMap.jsonObject["type"].toString().replace("\"", "")
        )

        val message = when (messageType) {
            Message.Type.PERSONAL -> Json.decodeFromString<PersonalMessage>(decryptedMessage.first)
            Message.Type.SYSTEM -> Json.decodeFromString<SystemMessage>(decryptedMessage.first)
        }
        message.status = Message.Status.DELIVERED

        when (message) {
            is SystemMessage -> {
                systemMessageHandler.handle(message)
            }
        }

        messageRepository.saveMessage(message)

        if (message !is SystemMessage) {
            val messageDeliveredMessage = SystemMessage(
                senderId = message.recipientId!!,
                recipientId = message.senderId,
                type = Message.Type.SYSTEM,
                data = SystemMessage.Data(
                    SystemMessage.Data.Type.MESSAGE_DELIVERED,
                    message.id
                ),
                chatId = message.chatId,
            )
            messageSender.sendMessage(messageDeliveredMessage)
        }

        Log.d("DATA", "MESSAGE $messageText")
    }

}