package com.ougi.serverinforepoapi.data.network

import com.ougi.coreutils.utils.Result
import com.ougi.serverinforepoapi.data.entities.WebSocketInfo

interface ServerInfoRepositoryNetworkApi {
    suspend fun getWebSocketInfo(userId: String): Result<WebSocketInfo?>
}