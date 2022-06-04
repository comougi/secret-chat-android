package com.ougi.userrepoimpl.data.repository

import android.util.Base64
import android.util.Log
import com.ougi.coreutils.utils.Result
import com.ougi.coreutils.utils.StringParser
import com.ougi.encryptionapi.data.EncryptionClientApi
import com.ougi.encryptionapi.data.KeyStorageApi
import com.ougi.userrepoapi.data.datastore.UserRepositoryDataStoreApi
import com.ougi.userrepoapi.data.network.UserRepositoryNetworkApi
import com.ougi.userrepoapi.data.repository.UserRepository
import com.ougi.userrepoimpl.R
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
            val userId = userIdDecrypted.first
            return if (userIdDecrypted.second) {
                userRepositoryDataStoreApi.saveUserId(userId)
                Result.Success(userId)
            } else Result.Error(R.string.bad_hash_check)
        }
        return userResult
    }

    override suspend fun isUserRegistered(isRegisteredId: String): Result<Boolean?> {
        val userId = userRepositoryDataStoreApi.readUserId()
            ?: return Result.Error(R.string.you_are_not_registered)
        val userIdEncrypted = encryptionClientApi.encryptViaDHAesKey(userId)
        Log.d("DATA", "'$userIdEncrypted'")
        val result =
            userRepositoryNetworkApi.isUserRegistered(userId, isRegisteredId, userIdEncrypted)
        if (result is Result.Success) {
            val hasUserIdEncrypted = result.data!!
            val hasUserIdDecrypted = encryptionClientApi.decryptViaDHAesKey(hasUserIdEncrypted)
            return if (hasUserIdDecrypted.second && hasUserIdDecrypted.first == isRegisteredId) {
                Result.Success(true)
            } else {
                Result.Error(R.string.bad_hash_check)
            }
        }
        val hasNoUserMessage =
            "${StringParser.parseToString(R.string.user_with_id)}" +
                    " $isRegisteredId " +
                    "${StringParser.parseToString(R.string.not_registered)}"
        val errorMessage =
            if (result.message() != null) hasNoUserMessage
            else com.ougi.ui.R.string.check_network_connection
        return Result.Error(errorMessage)
    }

}