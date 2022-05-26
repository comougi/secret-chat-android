package com.ougi.serverinforepoimpl.data.network

import com.ougi.coreutils.utils.Result
import com.ougi.networkapi.data.utils.SafeApiCallUtils
import com.ougi.serverinforepoapi.data.network.ServerInfoRepositoryNetworkApi
import javax.inject.Inject

class ServerInfoRepositoryNetworkApiImpl @Inject constructor(
    private val serverInfoRepositoryNetworkService: ServerInfoRepositoryNetworkService
) : ServerInfoRepositoryNetworkApi {

    override suspend fun getMessagingWebSocketConnectionLink(userId: String): Result<String?> {
        val call = serverInfoRepositoryNetworkService.getMessagingWebSocketConnectionLink(userId)
        return SafeApiCallUtils.safeApiCall(call)
    }

    override suspend fun getPushWebSocketConnectionLink(userId: String): Result<String?> {
        val call = serverInfoRepositoryNetworkService.getPushWebSocketConnectionLink(userId)
        return SafeApiCallUtils.safeApiCall(call)
    }

}