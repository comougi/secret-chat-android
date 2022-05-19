package com.ougi.serverinforepoimpl.data.network

import com.ougi.coreutils.utils.Result
import com.ougi.networkapi.data.utils.SafeApiCallUtils
import com.ougi.serverinforepoapi.data.network.ServerInfoRepositoryNetworkApi
import javax.inject.Inject

class ServerInfoRepositoryNetworkApiImpl @Inject constructor(
    private val serverInfoRepositoryNetworkService: ServerInfoRepositoryNetworkService
) : ServerInfoRepositoryNetworkApi {

    override suspend fun getWebSocketConnectionLink(userId: String): Result<String?> {
        val call = serverInfoRepositoryNetworkService.getWebSocketConnectionLink(userId)
        return SafeApiCallUtils.safeApiCall(call)
    }

}