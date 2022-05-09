package com.ougi.serverinforepoimpl.data.network

import com.ougi.coreutils.utils.Result
import com.ougi.networkapi.data.utils.SafeApiCallUtils
import com.ougi.serverinforepoapi.data.entities.WebSocketInfo
import com.ougi.serverinforepoapi.data.network.ServerInfoRepositoryNetworkApi
import javax.inject.Inject

class ServerInfoRepositoryNetworkApiImpl @Inject constructor(
    private val serverInfoRepositoryNetworkService: ServerInfoRepositoryNetworkService
) : ServerInfoRepositoryNetworkApi {

    override suspend fun getWebSocketInfo(userId: String): Result<WebSocketInfo?> {
        val call = serverInfoRepositoryNetworkService.getWebSocketInfo(userId)
        return SafeApiCallUtils.safeApiCall(call)
    }

}