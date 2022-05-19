package com.ougi.serverinforepoapi.data.network

import com.ougi.coreutils.utils.Result

interface ServerInfoRepositoryNetworkApi {
    suspend fun getWebSocketConnectionLink(userId: String): Result<String?>
}