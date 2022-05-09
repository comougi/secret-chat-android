package com.ougi.userrepoimpl.data.repository

import android.util.Base64
import com.ougi.coreutils.utils.Result
import com.ougi.coreutils.utils.SeparationUtils
import com.ougi.encryptionapi.data.EncryptionClientApi
import com.ougi.encryptionapi.data.utils.KeyStorageUtils
import com.ougi.userrepoapi.data.datastore.UserRepositoryDataStoreApi
import com.ougi.userrepoapi.data.entities.User
import com.ougi.userrepoapi.data.network.UserRepositoryNetworkApi
import com.ougi.userrepoapi.data.repository.UserRepository
import java.util.*
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRepositoryNetworkApi: UserRepositoryNetworkApi,
    private val encryptionClientApi: EncryptionClientApi,
    private val keyStorageUtils: KeyStorageUtils,
    private val userRepositoryDataStoreApi: UserRepositoryDataStoreApi
) : UserRepository {

    override suspend fun register(): Result<User?> {
        val id = UUID.randomUUID().toString()
        val encryptedId = encryptionClientApi.encryptViaSecretKey(id)
        val encryptedIdString =
            SeparationUtils.separate(encryptedId.first, encryptedId.second.joinToString(""))

        val publicKey = keyStorageUtils.readDhKeyPair().public
        val publicKeyStr = Base64.encodeToString(publicKey.encoded, Base64.DEFAULT)

        val user = User(encryptedIdString, publicKeyStr)

        val userResult = userRepositoryNetworkApi.register(user)

        if (userResult is Result.Success) {
            userRepositoryDataStoreApi.saveUserId(userResult.data!!.id)
        }
        return userResult
    }

}