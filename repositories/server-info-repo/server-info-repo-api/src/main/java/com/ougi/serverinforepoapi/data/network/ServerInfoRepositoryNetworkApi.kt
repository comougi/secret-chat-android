package com.ougi.serverinforepoapi.data.network

import com.ougi.coreutils.utils.Result

interface ServerInfoRepositoryNetworkApi {
    suspend fun getMessagingWebSocketConnectionLink(userId: String): Result<String?>
    suspend fun getPushWebSocketConnectionLink(userId: String): Result<String?>
}