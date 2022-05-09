package com.ougi.userrepoimpl.data.network

import com.ougi.coreutils.utils.Result
import com.ougi.networkapi.data.utils.SafeApiCallUtils
import com.ougi.userrepoapi.data.entities.User
import com.ougi.userrepoapi.data.network.UserRepositoryNetworkApi
import javax.inject.Inject

class UserRepositoryNetworkApiImpl @Inject constructor(
    private val userRepositoryNetworkService: UserRepositoryNetworkService
) : UserRepositoryNetworkApi {

    override suspend fun register(user: User): Result<User?> {
        return SafeApiCallUtils.safeApiCall(userRepositoryNetworkService.register(user))
    }


}