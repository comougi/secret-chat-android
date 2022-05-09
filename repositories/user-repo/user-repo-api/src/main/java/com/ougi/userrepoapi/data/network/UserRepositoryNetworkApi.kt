package com.ougi.userrepoapi.data.network

import com.ougi.coreutils.utils.Result
import com.ougi.userrepoapi.data.entities.User

interface UserRepositoryNetworkApi {
    suspend fun register(user: User): Result<User?>
}