package com.ougi.serverinforepoapi.data.repository

import com.ougi.coreutils.utils.Result

interface ServerInfoRepository {
    suspend fun getMessagingWebSocketConnectionLink(): Result<String?>
    suspend fun getPushWebSocketConnectionLink(): Result<String?>
}