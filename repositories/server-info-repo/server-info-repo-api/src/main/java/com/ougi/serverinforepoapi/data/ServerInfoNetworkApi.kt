package com.ougi.serverinforepoapi.data

import com.ougi.coreutils.utils.Result

interface ServerInfoNetworkApi {
    suspend fun getPublicKeyFromServer(): Result<String?>
}