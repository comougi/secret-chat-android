package com.ougi.userrepoimpl.data.network

import com.ougi.coreutils.utils.Result
import com.ougi.encryptionapi.data.entity.AesEncryptedData
import com.ougi.networkapi.data.utils.SafeApiCallUtils
import com.ougi.ui.R
import com.ougi.userrepoapi.data.network.UserRepositoryNetworkApi
import javax.inject.Inject

class UserRepositoryNetworkApiImpl @Inject constructor(
    private val userRepositoryNetworkService: UserRepositoryNetworkService
) : UserRepositoryNetworkApi {

    override suspend fun register(publicKeys: Map<String, String>): Result<AesEncryptedData?> {
        val call = userRepositoryNetworkService.register(publicKeys)
        return SafeApiCallUtils.safeApiCall(call, R.string.check_network_connection)
    }

    override suspend fun isUserRegistered(
        userId: String,
        isRegisteredId: String,
        encryptedUserId: String
    ): Result<AesEncryptedData?> {
        val call =
            userRepositoryNetworkService.isUserRegistered(userId, isRegisteredId, encryptedUserId)
        return SafeApiCallUtils.safeApiCall(call)
    }

}