package com.ougi.chatrepoimpl.data.repository

import com.ougi.chatrepoapi.data.database.ChatRepositoryDao
import com.ougi.chatrepoapi.data.entity.Chat
import com.ougi.chatrepoapi.data.network.ChatRepositoryNetworkApi
import com.ougi.chatrepoapi.data.repository.ChatRepository
import com.ougi.chatrepoimpl.R
import com.ougi.coreutils.utils.Result
import com.ougi.encryptionapi.data.EncryptionClientApi
import com.ougi.userrepoapi.data.datastore.UserRepositoryDataStoreApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEmpty
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
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
        val userIdsJson = Json.encodeToString(userIds)
        val createChatResult = chatRepositoryNetworkApi.createChatWithUsers(userId, userIdsJson)
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
        val message = createChatResult.message() ?: R.string.check_network_connection

        return Result.Error(message)
    }

    override fun getChats(): Flow<Result<List<Chat>?>> = chatRepositoryDao.getAllChatsFlow()
        .map { list -> Result.Success(list) as Result<List<Chat>?> }
        .catch { emit(Result.Error()) }
        .onEmpty { emit(Result.Loading()) }

}