package com.ougi.serverinforepoapi.data

import com.ougi.coreutils.utils.Result
import com.ougi.serverinforepoapi.data.entities.WebSocketInfo

interface ServerInfoNetworkApi {
   suspend fun getWebSocketInfo(userId: String): Result<WebSocketInfo?>
}