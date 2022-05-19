package com.ougi.serverinforepoapi.data.repository

import com.ougi.coreutils.utils.Result

interface ServerInfoRepository {
    suspend fun getWebSocketConnectionLink(): Result<String?>
}