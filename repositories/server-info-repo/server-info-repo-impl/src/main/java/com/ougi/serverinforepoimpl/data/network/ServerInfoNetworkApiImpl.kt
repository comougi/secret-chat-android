package com.ougi.serverinforepoimpl.data.network

import com.ougi.coreutils.utils.Result
import com.ougi.networkapi.data.utils.SafeApiCallUtils
import com.ougi.serverinforepoapi.data.ServerInfoDataStoreApi
import com.ougi.serverinforepoapi.data.ServerInfoNetworkApi
import com.ougi.serverinforepoapi.data.entities.WebSocketInfo
import javax.inject.Inject

class ServerInfoNetworkApiImpl @Inject constructor(
    private val serverInfoNetworkService: ServerInfoNetworkService,
    private val serverInfoDataStoreApi: ServerInfoDataStoreApi
) : ServerInfoNetworkApi {

    override suspend fun getWebSocketInfo(userId: String): Result<WebSocketInfo?> {
        val call = serverInfoNetworkService.getWebSocketInfo(userId)
        val result = SafeApiCallUtils.safeApiCall(call)

        if (result is Result.Success) {
            val publicKey = result.data!!.publicKey
            if (serverInfoDataStoreApi.readPublicKeyString() != publicKey)
                serverInfoDataStoreApi.savePublicKey(publicKey)
        }

        return result
    }

}