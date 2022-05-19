package com.ougi.userrepoapi.data.network

import com.ougi.coreutils.utils.Result

interface UserRepositoryNetworkApi {
    suspend fun register(publicKey: String): Result<String?>
}