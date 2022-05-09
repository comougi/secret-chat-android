package com.ougi.serverinforepoapi.data.repository

import com.ougi.coreutils.utils.Result
import com.ougi.serverinforepoapi.data.entities.WebSocketInfo

interface ServerInfoRepository {
    suspend fun getWebSocketInfo(userId: String): Result<WebSocketInfo?>
}