package com.ougi.userrepoimpl.data.repository

import android.util.Base64
import android.util.Log
import com.ougi.coreutils.utils.Result
import com.ougi.encryptionapi.data.EncryptionClientApi
import com.ougi.encryptionapi.data.KeyStorageApi
import com.ougi.userrepoapi.data.datastore.UserRepositoryDataStoreApi
import com.ougi.userrepoapi.data.network.UserRepositoryNetworkApi
import com.ougi.userrepoapi.data.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRepositoryNetworkApi: UserRepositoryNetworkApi,
    private val keyStorageApi: KeyStorageApi,
    private val userRepositoryDataStoreApi: UserRepositoryDataStoreApi,
    private val encryptionClientApi: EncryptionClientApi
) : UserRepository {

    override suspend fun register(): Result<String?> {
        val publicKey = keyStorageApi.readDhKeyPair().public
        val publicKeyStr = Base64.encodeToString(publicKey.encoded, Base64.NO_WRAP)
        val userResult = userRepositoryNetworkApi.register(publicKeyStr)

        if (userResult is Result.Success) {
            val userIdEncrypted = userResult.data!!
            val userIdDecrypted = encryptionClientApi.decryptViaDHAesKey(userIdEncrypted)
            val userIdDecoded = Base64.decode(userIdDecrypted.first, Base64.DEFAULT)
            val userId = userIdDecoded.decodeToString()
            return if (userIdDecrypted.second) {
                userRepositoryDataStoreApi.saveUserId(userId)
                Log.d("DATA", "HASH userId $userId")
                Result.Success(userId)
            } else Result.Error(userResult.message())
        }
        return userResult
    }

}