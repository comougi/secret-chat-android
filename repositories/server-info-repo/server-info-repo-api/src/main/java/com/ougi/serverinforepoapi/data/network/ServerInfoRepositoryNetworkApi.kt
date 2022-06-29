package com.ougi.serverinforepoapi.data.network

import com.ougi.coreutils.utils.Result
import com.ougi.encryptionapi.data.entity.AesEncryptedData

interface ServerInfoRepositoryNetworkApi {
    suspend fun getMessagingWebSocketConnectionLink(
        userId: String,
        userIdEncrypted: String
    ): Result<AesEncryptedData?>

    suspend fun getPushWebSocketConnectionLink(userId: String): Result<String?>
}