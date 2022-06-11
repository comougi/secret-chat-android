package com.ougi.userrepoapi.data.network

import com.ougi.coreutils.utils.Result
import com.ougi.encryptionapi.data.entity.AesEncryptedData

interface UserRepositoryNetworkApi {
    suspend fun register(publicKeys: Map<String, String>): Result<AesEncryptedData?>
    suspend fun isUserRegistered(
        userId: String,
        isRegisteredId: String,
        encryptedUserId: String
    ): Result<AesEncryptedData?>
}