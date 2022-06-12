package com.ougi.chatscreenimpl.domain.usecase

import android.util.Base64
import com.ougi.chatrepoapi.data.repository.ChatRepository
import com.ougi.encryptionapi.data.KeyStorageApi
import com.ougi.messagerepoapi.data.database.PersonalMessageDatabaseDao
import com.ougi.messagerepoapi.data.entities.Message
import com.ougi.messagerepoapi.data.entities.PersonalMessage
import com.ougi.messagerepoapi.data.repository.MessageRepository
import com.ougi.messagingapi.data.MessageSender
import com.ougi.userrepoapi.data.repository.UserRepository
import javax.inject.Inject

interface SendMessageUseCase {
    suspend fun sendMessage(messageText: String, chatId: String)
}

class SendMessageUseCaseImpl @Inject constructor(
    private val messageSender: MessageSender,
    private val userRepository: UserRepository,
    private val chatRepository: ChatRepository,
    private val personalMessageDatabaseDao: PersonalMessageDatabaseDao,
    private val keyStorageApi: KeyStorageApi,
    private val messageRepository: MessageRepository
) : SendMessageUseCase {

    override suspend fun sendMessage(messageText: String, chatId: String) {
        val chat = chatRepository.getChatById(chatId)
            ?: throw NullPointerException("Has no chat with id $chatId")

        val userPublicKey = keyStorageApi.readRsaKeyPair().public
        val userPublicKeyString = Base64.encodeToString(userPublicKey.encoded, Base64.NO_WRAP)
        val internalEncryptedMessage =
            messageRepository.encryptMessageData(messageText, userPublicKeyString)
        val dbMessage = createMessage(
            encryptedMessage = internalEncryptedMessage,
            chatId = chatId
        )
        dbMessage.status = Message.Status.SENDING

        personalMessageDatabaseDao.insertMessage(dbMessage)

        chat.users.forEach { user ->
            val message = createMessage(
                encryptedMessage = messageText,
                chatId = chatId,
                recipientId = user.id
            )
            messageSender.sendMessage(message, user.rsaPublicKey)
        }
    }

    private suspend fun createMessage(
        encryptedMessage: String,
        chatId: String,
        recipientId: String? = null
    ): PersonalMessage {
        val userId = userRepository.getUserId()
        val status =
            if (recipientId == null) Message.Status.SENDING
            else null

        return PersonalMessage(
            senderId = userId,
            chatId = chatId,
            recipientId = recipientId,
            data = encryptedMessage,
            type = Message.Type.PERSONAL,
            status = status
        )
    }

}