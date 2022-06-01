package com.ougi.chatrepoimpl.data.repository

import com.ougi.chatrepoapi.data.database.ChatRepositoryDao
import com.ougi.chatrepoapi.data.entity.Chat
import com.ougi.chatrepoapi.data.network.ChatRepositoryNetworkApi
import com.ougi.chatrepoapi.data.repository.ChatRepository
import com.ougi.coreutils.utils.Result
import com.ougi.encryptionapi.data.EncryptionClientApi
import com.ougi.userrepoapi.data.datastore.UserRepositoryDataStoreApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val chatRepositoryNetworkApi: ChatRepositoryNetworkApi,
    private val chatRepositoryDao: ChatRepositoryDao,
    private val userRepositoryDataStoreApi: UserRepositoryDataStoreApi,
    private val encryptionClientApi: EncryptionClientApi
) : ChatRepository {

    override suspend fun createChat(userIds: List<String>): Result<Chat?> {
        val userId = userRepositoryDataStoreApi.readUserId() ?: return Result.Error()
        val createChatResult = chatRepositoryNetworkApi.createChatWithUsers(userId, userIds)
        if (createChatResult is Result.Success) {
            val chatJsonEncrypted = createChatResult.data!!
            val chatJsonDecrypted = encryptionClientApi.decryptViaDHAesKey(chatJsonEncrypted)
            return if (chatJsonDecrypted.second) {
                val chat = Json.decodeFromString<Chat>(chatJsonDecrypted.first)
                chatRepositoryDao.insertChat(chat)
                Result.Success(chat)
            } else
                Result.Error()
        }
        return Result.Error(createChatResult.message())
    }

}