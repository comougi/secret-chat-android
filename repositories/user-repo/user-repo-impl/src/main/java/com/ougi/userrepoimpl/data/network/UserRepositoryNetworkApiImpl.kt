package com.ougi.userrepoimpl.data.network

import com.ougi.coreutils.utils.Result
import com.ougi.networkapi.data.utils.SafeApiCallUtils
import com.ougi.ui.R
import com.ougi.userrepoapi.data.network.UserRepositoryNetworkApi
import javax.inject.Inject

class UserRepositoryNetworkApiImpl @Inject constructor(
    private val userRepositoryNetworkService: UserRepositoryNetworkService
) : UserRepositoryNetworkApi {

    override suspend fun register(publicKey: String): Result<String?> {
        val call = userRepositoryNetworkService.register(publicKey)
        return SafeApiCallUtils.safeApiCall(call, R.string.check_network_connection)
    }

}