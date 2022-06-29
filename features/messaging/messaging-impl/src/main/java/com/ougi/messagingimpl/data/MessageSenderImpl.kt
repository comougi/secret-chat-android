package com.ougi.messagingimpl.data

import com.ougi.encryptionapi.data.EncryptionClientApi
import com.ougi.messagerepoapi.data.entities.Message
import com.ougi.messagerepoapi.data.entities.PersonalMessage
import com.ougi.messagerepoapi.data.entities.SystemMessage
import com.ougi.messagerepoapi.data.repository.MessageRepository
import com.ougi.messagingapi.data.MessageSender
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class MessageSenderImpl @Inject constructor(
    private val encryptionClientApi: EncryptionClientApi,
    private val messageRepository: MessageRepository
) :
    MessageSender {

    override val messages: MutableStateFlow<Pair<String, String>?> = MutableStateFlow(null)

    @OptIn(ExperimentalSerializationApi::class)
    private val json = Json { explicitNulls = false }

    override suspend fun sendMessage(message: Message, publicKeyStr: String, encrypted: Boolean) {

        if (encrypted) {
            val encryptedMessageData =
                messageRepository.encryptMessageData(message.data, publicKeyStr)
            message.data = encryptedMessageData
        }

        val jsonMessage = when (message) {
            is PersonalMessage -> json.encodeToString(message)
            is SystemMessage -> json.encodeToString(message)
            else -> throw IllegalArgumentException("Unknown message type")
        }

        val encryptedMessage = encryptionClientApi.encryptViaDHAesKey(jsonMessage)

        messages.value = message.id to encryptedMessage
    }

    companion object {
        private var INSTANCE: MessageSender? = null
        fun getInstance(
            encryptionClientApi: EncryptionClientApi,
            messageRepository: MessageRepository
        ): MessageSender {
            return INSTANCE ?: MessageSenderImpl(encryptionClientApi, messageRepository)
                .also { sender -> INSTANCE = sender }
        }
    }
}