package com.ougi.chatrepoapi.data.network

import com.ougi.coreutils.utils.Result
import com.ougi.encryptionapi.data.entity.AesEncryptedData

interface ChatRepositoryNetworkApi {
    suspend fun createChatWithUsers(userId: String, userIds: String): Result<AesEncryptedData?>
}