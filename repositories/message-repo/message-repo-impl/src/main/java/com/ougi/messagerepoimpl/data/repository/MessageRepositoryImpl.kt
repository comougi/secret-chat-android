package com.ougi.messagerepoimpl.data.repository

import android.util.Base64
import com.ougi.encryptionapi.data.EncryptionClientApi
import com.ougi.encryptionapi.data.utils.EncryptionUtils
import com.ougi.encryptionapi.data.utils.KeyGenerationUtils
import com.ougi.messagerepoapi.data.database.PersonalMessageDatabaseDao
import com.ougi.messagerepoapi.data.database.SystemMessageDatabaseDao
import com.ougi.messagerepoapi.data.entities.EncryptedMessage
import com.ougi.messagerepoapi.data.entities.Message
import com.ougi.messagerepoapi.data.entities.PersonalMessage
import com.ougi.messagerepoapi.data.entities.SystemMessage
import com.ougi.messagerepoapi.data.repository.MessageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.crypto.spec.SecretKeySpec
import javax.inject.Inject

class MessageRepositoryImpl @Inject constructor(
    private val personalMessageDatabaseDao: PersonalMessageDatabaseDao,
    private val systemMessageDatabaseDao: SystemMessageDatabaseDao,
    private val keyGenerationUtils: KeyGenerationUtils,
    private val encryptionClientApi: EncryptionClientApi,
    private val encryptionUtils: EncryptionUtils,
) : MessageRepository {

    override suspend fun saveMessage(message: Message) {
        when (message) {
            is PersonalMessage -> personalMessageDatabaseDao.insertMessage(message)
            is SystemMessage -> systemMessageDatabaseDao.insertMessage(message)
        }
    }

    override suspend fun updateMessageStatus(messageId: String, status: Message.Status) {
        val systemMessage = systemMessageDatabaseDao.getMessageById(messageId)
        val personalMessage = personalMessageDatabaseDao.getMessageById(messageId)

        if (systemMessage != null) {
            systemMessage.status = status
            systemMessageDatabaseDao.updateMessage(systemMessage)
        }
        if (personalMessage != null) {
            personalMessage.status = status
            personalMessageDatabaseDao.updateMessage(personalMessage)
        }

    }

    override fun encryptMessageData(data: String, publicKey: String): String {
        val aesKey = keyGenerationUtils.generateAesKey()
        val aesKeyString = Base64.encodeToString(aesKey.encoded, Base64.NO_WRAP)

        val encryptedData = encryptionUtils.encryptViaSecretKey(data, aesKey)

        val aesKeyEncrypted = encryptionClientApi.encryptViaRsaKey(aesKeyString, publicKey)

        val encryptedMessage = EncryptedMessage(
            encryptedAesKey = aesKeyEncrypted,
            encryptedMessage = encryptedData
        )

        return Json.encodeToString(encryptedMessage)
    }

    override suspend fun decryptMessageData(encryptedData: String): Pair<String, Boolean> {
        val encryptedMessage = Json.decodeFromString<EncryptedMessage>(encryptedData)

        val aesKeyDecrypted = encryptionClientApi.decryptViaRsaKey(encryptedMessage.encryptedAesKey)

        val aesKeyBytes = Base64.decode(aesKeyDecrypted, Base64.NO_WRAP)
        val aesKey = SecretKeySpec(aesKeyBytes, "AES")

        return encryptionUtils.decryptViaSecretKey(encryptedMessage.encryptedMessage, aesKey)
    }

    override suspend fun deleteMessage(message: Message) {
        when (message) {
            is PersonalMessage -> personalMessageDatabaseDao.deleteMessage(message)
            is SystemMessage -> systemMessageDatabaseDao.deleteMessage(message)
        }
    }

    override suspend fun getSystemMessages(): List<SystemMessage>? {
        return systemMessageDatabaseDao.getAllSystemMessages()
    }

    override fun personalMessagesByChatId(chatId: String): Flow<List<PersonalMessage>> {
        return personalMessageDatabaseDao.getChatMessagesFlow(chatId)
    }
}